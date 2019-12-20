package com.jacken.ttsbservicecore.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jacken.ttsbservicecore.mapper.TbkFavoritesMapper;
import com.jacken.ttsbservicecore.mapper.TbkItemGoodsMapper;
import com.jacken.ttsbservicecore.service.TbkGoodsService;
import com.jacken.wqttsbcommon.config.TbkConfig;
import com.jacken.wqttsbmodel.baseentity.PageModel;
import com.jacken.wqttsbmodel.entity.TbkFavorites;
import com.jacken.wqttsbmodel.entity.TbkItemGoods;
import com.jacken.wqttsbmodel.enums.ExceptionEnum;
import com.jacken.wqttsbmodel.request.TbkGoodsRequest;
import com.jacken.wqttsbmodel.result.Result;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkUatmFavoritesGetRequest;
import com.taobao.api.request.TbkUatmFavoritesItemGetRequest;
import com.taobao.api.response.TbkUatmFavoritesGetResponse;
import com.taobao.api.response.TbkUatmFavoritesItemGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
@Service
@Slf4j
public class TbkGoodsServiceImpl extends ServiceImpl<TbkItemGoodsMapper, TbkItemGoods> implements TbkGoodsService {

    @Autowired
    private TbkConfig taoBaoKeApiConfig;

    @Autowired
    private TbkFavoritesMapper tbkFavoritesMapper;

    @Autowired
    private  TbkItemGoodsMapper tbkItemGoodsMapper;
    @Override
    public Result getTbkGoodsItem() {

        long begin = System.currentTimeMillis();
        //1.首先同步商品包信息
        getGoodsFavorites();
        //2.删除所有商品信息
        tbkItemGoodsMapper.deleteAllTbkGoods();
        log.info("淘宝客删除选品库商品信息同步开始");
        TaobaoClient client = new DefaultTaobaoClient(taoBaoKeApiConfig.getServerUrl(), taoBaoKeApiConfig.getAppKey(), taoBaoKeApiConfig.getAppSecret());
        List<TbkFavorites> favoritesList = tbkFavoritesMapper.selectList(null);
        //封装request请求参数
        TbkUatmFavoritesItemGetRequest req = getTbkUatmFavoritesItemGetRequest();
        for (TbkFavorites favorites : favoritesList) {
            req.setFavoritesId(Long.valueOf(favorites.getFavoritesId()));
            Result<Integer> tbkTotalNum = getTbkTotalNum(Long.valueOf(favorites.getFavoritesId()));
            Integer data = tbkTotalNum.getData();
            if(null!=data){
                if(data>100){
                    if(data%100>0){
                        int page=data/100+1;
                        for (int i = 0; i < page; i++) {
                            req.setPageNo(Long.valueOf(i+1));
                            req.setPageSize(100L);
                            TbkUatmFavoritesItemGetResponse rsp = null;
                            try {
                                rsp = client.execute(req);
                                JSONObject responseJson = JSONObject.parseObject(rsp.getBody());
                                JSONObject json = responseJson.getJSONObject("tbk_uatm_favorites_item_get_response");
                                if(json!=null){
                                    JSONArray jsonItem = responseJson.getJSONObject("tbk_uatm_favorites_item_get_response").getJSONObject("results").getJSONArray("uatm_tbk_item");
                                    //获取淘宝商品详情
                                    getGoodItemDetail(jsonItem,favorites);
                                }
                            } catch (ApiException e) {
                                log.error("淘宝客获取选品库商品信息失败"+e.getMessage());
                            }
                        }
                    }
                }else {
                    req.setPageSize(100L);
                    req.setPageNo(1L);
                    TbkUatmFavoritesItemGetResponse rsp = null;
                    try {
                        rsp = client.execute(req);
                        JSONObject responseJson = JSONObject.parseObject(rsp.getBody());
                        JSONObject json = responseJson.getJSONObject("tbk_uatm_favorites_item_get_response");
                        if(json!=null){
                            JSONArray jsonItem = responseJson.getJSONObject("tbk_uatm_favorites_item_get_response").getJSONObject("results").getJSONArray("uatm_tbk_item");
                            //获取淘宝商品详情
                            getGoodItemDetail(jsonItem,favorites);
                        }
                    } catch (ApiException e) {
                        log.error("淘宝客获取选品库商品信息失败"+e.getMessage());
                    }
                }
            }
        }
        long end = System.currentTimeMillis();
        log.info("同步淘宝商品共耗时:"+((end-begin)/1000)+"s");
        return Result.success();
    }

    //todo  分页
    @Override
    public Result selectTbkGoodsPage(TbkGoodsRequest request) {
        IPage<Map<String, Object>> page = new Page<>(request.getCurrentPage(), request.getPageSize());
        List<Map<String, Object>> list;
        list = tbkItemGoodsMapper.selectTbkGoodsListPage(page, request);
        return Result.success(PageModel.buildByIPage(page.setRecords(list)));
    }

    public  Result getGoodsFavorites(){
        //删除所有的商品包信息
        tbkFavoritesMapper.deleteAllFavorites();
        TaobaoClient client = new DefaultTaobaoClient(taoBaoKeApiConfig.getServerUrl(), taoBaoKeApiConfig.getAppKey(), taoBaoKeApiConfig.getAppSecret());
        TbkUatmFavoritesGetRequest req = new TbkUatmFavoritesGetRequest();
        req.setPageNo(1L);
        req.setPageSize(100L);
        req.setFields("favorites_title,favorites_id,type");
        req.setType(-1L);
        TbkUatmFavoritesGetResponse rsp = null;
        try {
            rsp = client.execute(req);
            JSONObject responseJson = JSONObject.parseObject(rsp.getBody());
            JSONArray resultsJson = responseJson.getJSONObject("tbk_uatm_favorites_get_response")
                    .getJSONObject("results").getJSONArray("tbk_favorites");
            //遍历resultJson 获取商品包信息
            for (int i = 0; i < resultsJson.size(); i++) {
                TbkFavorites favorites = new TbkFavorites();
                String s = resultsJson.get(i).toString();
                JSONObject jsonObject = JSONObject.parseObject(s);
                //保存选品库id
                favorites.setFavoritesId(Integer.parseInt(jsonObject.getString("favorites_id")));
                //保存选品库title
                favorites.setFavoritesTitle(jsonObject.getString("favorites_title"));
                favorites.setType(jsonObject.getInteger("type"));

                Result<Integer> resultModel = getTbkTotalNum(Long.valueOf(jsonObject.getString("favorites_id")));
                favorites.setTotalResult(resultModel.getData());
                //根据选品库id查询商品包信息
                favorites.setUpdateTime(new Date());
                favorites.setCreateTime(new Date());
                tbkFavoritesMapper.insert(favorites);
                log.info("淘宝客获取选品库入库成功"+JSONObject.toJSONString(favorites.getFavoritesId()));
            }
        } catch (ApiException e) {
            log.error("淘宝客获取选品库失败"+e.getMessage());
        }

        return Result.success();
    }

    /**
     * 根据选品库id查询商品总数量
     * @param favorietsId
     * @return
     */
    public  Result getTbkTotalNum(Long favorietsId){
        TbkUatmFavoritesItemGetRequest req = new TbkUatmFavoritesItemGetRequest();
        req.setPageNo(1L);
        req.setPageSize(100L);
        req.setPlatform(1L);
        req.setAdzoneId(Long.valueOf(taoBaoKeApiConfig.getAdzoneId()));
        req.setUnid("3456");
        req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick,shop_title,zk_final_price_wap,event_start_time,event_end_time,tk_rate,status,type,total_results,category,coupon_click_url,coupon_end_time,coupon_info,coupon_start_time,coupon_total_count,coupon_remain_count");
        req.setFavoritesId(favorietsId);
        TaobaoClient client = new DefaultTaobaoClient(taoBaoKeApiConfig.getServerUrl(), taoBaoKeApiConfig.getAppKey(), taoBaoKeApiConfig.getAppSecret());
        TbkUatmFavoritesItemGetResponse rsp = null;
        try {
            rsp = client.execute(req);
            JSONObject responseJson = JSONObject.parseObject(rsp.getBody());
            JSONObject json = responseJson.getJSONObject("tbk_uatm_favorites_item_get_response");
            if(json!=null&&json.getInteger("total_results")!=null){
                return Result.success(json.getInteger("total_results"));
            }else {
                return Result.error(ExceptionEnum.NOT_GOODS);
            }

        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 封装查询所有选品库信息 todo 分页未做
     */
    public  TbkUatmFavoritesItemGetRequest getTbkUatmFavoritesItemGetRequest(){
        TbkUatmFavoritesItemGetRequest req = new TbkUatmFavoritesItemGetRequest();
        req.setPlatform(1L);
        req.setPageSize(100L);
        req.setAdzoneId(Long.valueOf(taoBaoKeApiConfig.getAdzoneId()));
        req.setUnid("3456");
        req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick,shop_title,zk_final_price_wap,event_start_time,event_end_time,tk_rate,status,type,total_results,category,coupon_click_url,coupon_end_time,coupon_info,coupon_start_time,coupon_total_count,coupon_remain_count");
        req.setPageNo(1L);
        return req;
    }

    /**
     * 获取商品详情
     * @param itemJson
     * @param favorites
     */
    public  void  getGoodItemDetail(JSONArray itemJson,TbkFavorites favorites){
        for (int i = 0; i < itemJson.size(); i++) {
            try {
                TbkItemGoods tbkItemGoods = new TbkItemGoods();
                String item = itemJson.get(i).toString();
                JSONObject jsonItem = JSONObject.parseObject(item);
                //设置图片信息
                tbkItemGoods.setPictUrl(jsonItem.getString("pict_url"));
                tbkItemGoods.setItemUrl(jsonItem.getString("item_url"));
                tbkItemGoods.setUpdateTime(new Date());
                tbkItemGoods.setNick(jsonItem.getString("nick"));
                //设置skuid相当于
                tbkItemGoods.setNumIid( jsonItem.getLong("num_iid"));
                tbkItemGoods.setProvcity(jsonItem.getString("provcity"));
                tbkItemGoods.setReservePrice(jsonItem.getBigDecimal("reserve_price"));
                tbkItemGoods.setSellerId(jsonItem.getInteger("seller_id"));
                JSONObject jsonObject = JSONObject.parseObject(jsonItem.getString("small_images"));

                //设置小图
                tbkItemGoods.setSmallImages(jsonObject.getString("string"));
                tbkItemGoods.setCreateTime(new Date());
                tbkItemGoods.setZkFinalPriceWap(jsonItem.getBigDecimal("zk_final_price_wap"));
                tbkItemGoods.setVolume(jsonItem.getInteger("volume"));
                tbkItemGoods.setUserType(jsonItem.getInteger("user_type"));
                tbkItemGoods.setTkRate(jsonItem.getString("tk_rate"));
                tbkItemGoods.setTitle(jsonItem.getString("title"));
                tbkItemGoods.setShopTitle(jsonItem.getString("shop_title"));
                tbkItemGoods.setStatus(jsonItem.getInteger("status"));
                tbkItemGoods.setCouponEndTime(jsonItem.getString("coupon_end_time"));
                tbkItemGoods.setCouponStartTime(jsonItem.getString("coupon_start_time"));
                tbkItemGoods.setCouponTotalCount(jsonItem.getInteger("coupon_total_count"));
                tbkItemGoods.setCouponRemainCount(jsonItem.getInteger("coupon_remain_count"));
                tbkItemGoods.setCategory(jsonItem.getLong("category"));
                tbkItemGoods.setFavoritesId(favorites.getFavoritesId());
                tbkItemGoods.setFavoritesTitle(favorites.getFavoritesTitle());
                tbkItemGoods.setSource("淘宝");
                tbkItemGoods.setCouponClickUrl(jsonItem.getString("coupon_click_url"));
                tbkItemGoods.setCouponInfo(jsonItem.getString("coupon_info"));
                //获取优惠券值以及计算佣金
                String couponInfo = jsonItem.getString("coupon_info");
                String tkRate = jsonItem.getString("tk_rate");
                String zkFinalPriceWap = jsonItem.getString("zk_final_price_wap");
                String couponValue = Arrays.asList(Arrays.asList(couponInfo.split("减")).get(1).split("元")).get(0);
                tbkItemGoods.setCouponValue(Integer.parseInt(couponValue));
                //佣金
                BigDecimal subtract = new BigDecimal(zkFinalPriceWap).subtract(new BigDecimal(couponValue));
                //设置佣金
                tbkItemGoods.setCommission(subtract.multiply(new BigDecimal(tkRate).divide(new BigDecimal(100))));
                //保存商品信息
                tbkItemGoods.setCreateTime(new Date());
                tbkItemGoods.setUpdateTime(new Date());
                tbkItemGoodsMapper.insert(tbkItemGoods);
                log.info("淘宝客获取选品库商品信息入库成功--->--"+JSONObject.toJSONString(tbkItemGoods.getNumIid()));

            } catch (Exception e) {
                log.error("选品库保存失败--->"+e.getMessage()+"num_iid--->"+JSONObject.parseObject(itemJson.get(i).toString()).getLong("num_iid")
                        +"pict_url:"+JSONObject.parseObject(itemJson.get(i).toString()).getString("pict_url")
                        +"nick:"+JSONObject.parseObject(itemJson.get(i).toString()).getString("nick")
                        +"item_url:"+JSONObject.parseObject(itemJson.get(i).toString()).getString("item_url")
                        +"provcity:"+JSONObject.parseObject(itemJson.get(i).toString()).getString("provcity")
                        +"reserve_price:"+JSONObject.parseObject(itemJson.get(i).toString()).getBigDecimal("reserve_price")
                        +"seller_id:"+JSONObject.parseObject(itemJson.get(i).toString()).getInteger("seller_id")
                        +"small_images:"+JSONObject.parseObject(itemJson.get(i).toString()).getString("small_images")
                        +"zk_final_price_wap:"+JSONObject.parseObject(itemJson.get(i).toString()).getBigDecimal("zk_final_price_wap")
                        +"volume:"+JSONObject.parseObject(itemJson.get(i).toString()).getInteger("volume")
                        +"user_type:"+JSONObject.parseObject(itemJson.get(i).toString()).getInteger("user_type")
                        +"tk_rate:"+JSONObject.parseObject(itemJson.get(i).toString()).getString("tk_rate")
                        +"title:"+JSONObject.parseObject(itemJson.get(i).toString()).getString("title")
                        +"shop_title:"+JSONObject.parseObject(itemJson.get(i).toString()).getString("shop_title")
                        +"status:"+JSONObject.parseObject(itemJson.get(i).toString()).getInteger("status")
                        +"coupon_end_time:"+JSONObject.parseObject(itemJson.get(i).toString()).getString("coupon_end_time")
                        +"coupon_start_time:"+JSONObject.parseObject(itemJson.get(i).toString()).getString("coupon_start_time")
                        +"coupon_total_count:"+JSONObject.parseObject(itemJson.get(i).toString()).getInteger("coupon_total_count")
                        +"coupon_remain_count:"+JSONObject.parseObject(itemJson.get(i).toString()).getInteger("coupon_remain_count")
                        +"category:"+JSONObject.parseObject(itemJson.get(i).toString()).getInteger("category")
                        +"coupon_click_url:"+JSONObject.parseObject(itemJson.get(i).toString()).getString("coupon_click_url")
                        +"coupon_info:"+JSONObject.parseObject(itemJson.get(i).toString()).getString("coupon_info")
                );
            }
        }
    }
}
