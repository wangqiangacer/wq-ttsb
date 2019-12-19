package com.jacken.ttsbservicecore.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jacken.ttsbservicecore.mapper.JdGoodsMapper;
import com.jacken.ttsbservicecore.service.JdGoodsService;
import com.jacken.wqttsbcommon.config.JdGoodsApiConfig;
import com.jacken.wqttsbcommon.utils.DateUtils;
import com.jacken.wqttsbcommon.utils.HttpClientUtil;
import com.jacken.wqttsbmodel.baseentity.PageModel;
import com.jacken.wqttsbmodel.entity.JdGoods;
import com.jacken.wqttsbmodel.entity.JdGoodsPackage;
import com.jacken.wqttsbmodel.request.JdGoodsRequest;
import com.jacken.wqttsbmodel.result.Result;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.domain.kplware.ExternalService.response.getpkglist.BasePkg;
import com.jd.open.api.sdk.internal.util.CodecUtil;
import com.jd.open.api.sdk.internal.util.StringUtil;
import com.jd.open.api.sdk.request.kplware.KeplerXuanpinGetpkglistRequest;
import com.jd.open.api.sdk.request.kplware.KeplerXuanpinGetskuidlistRequest;
import com.jd.open.api.sdk.request.kplware.ProductBaseQueryRequest;
import com.jd.open.api.sdk.request.kplyxnl.KplOpenPromotionPidurlconvertRequest;
import com.jd.open.api.sdk.response.kplware.KeplerXuanpinGetpkglistResponse;
import com.jd.open.api.sdk.response.kplware.KeplerXuanpinGetskuidlistResponse;
import com.jd.open.api.sdk.response.kplware.ProductBaseQueryResponse;
import com.jd.open.api.sdk.response.kplyxnl.KplOpenPromotionPidurlconvertResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service
@SuppressWarnings("ALL")
@Slf4j
public class JdGoodsServiceImpl extends ServiceImpl<JdGoodsMapper, JdGoods> implements JdGoodsService {

    @Resource
    JdGoodsMapper jdGoodsMapper;

    @Autowired
    private JdGoodsApiConfig config;
    @Override
    public Result selectAllJdGoods() {
        return Result.success(jdGoodsMapper.selectList(null));
    }

    @Override
    public Result selectJdGoodsPage(JdGoodsRequest request) {
        IPage<Map<String, Object>> page = new Page<>(request.getCurrentPage(), request.getPageSize());
        List<Map<String, Object>> list;
        list = jdGoodsMapper.selectCreditCountListPage(page, request);
        return Result.success(PageModel.buildByIPage(page.setRecords(list)));
    }

    @Override
    public Result getJdGoodsItem() {
        //同步商品包信息
        getJdPkgList();
        jdGoodsMapper.deleteAllJdGoods();
        //jdGoodsMapper.delete(null);
        log.info("同步京东商品开始,清空所有商品信息");
        long begin = System.currentTimeMillis();
        JdClient client=new DefaultJdClient(config.getServerUrl(),config.getToken(),config.getApiKey(),config.getApiSecret());
        log.info("config.getServerUrl()-->"+config.getServerUrl());
        log.info("config.getToken()-->"+config.getToken());
        KeplerXuanpinGetpkglistRequest request=new KeplerXuanpinGetpkglistRequest();
        request.setMode(0);
        request.setVersion("1.0");
        request.setTimestamp(DateUtils.formatDate(new Date(),DateUtils.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
        KeplerXuanpinGetpkglistResponse response= null;
        try {
            response = client.execute(request);
            BasePkg[] list = response.getGetpkglistResult().getList();
            for (int i = 0; i < list.length; i++) {
                //获取商品包下的skuNum
                int skuNum = Integer.parseInt(String.valueOf(list[i].getSkuNum()));
                int packageId = Integer.parseInt(String.valueOf(list[i].getId()));
                //获取商品包的名称
                String packageName = list[i].getName();
                if(skuNum>100){
                    //分页取模如果大于0 则说明当前页有值
                    if(skuNum%100>0){
                        int page = skuNum / 100+1;
                        //每一次分页
                        for (int i1 = 0; i1 < page; i1++) {
                            //商品skuNum数量大于100的分页逻辑
                            selectJdGoodsByPackage(Long.valueOf(packageId), packageName,100*(i1+1)-100*i1,(i1+1));
                        }
                    }
                }else {
                    //商品每页大小小于100的逻辑
                    selectJdGoodsByPackage(Long.valueOf(packageId), packageName,100,1);
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        long end = System.currentTimeMillis();
        log.info("同步京东商品共耗时:"+((end-begin)/1000)+"s");
        return Result.success();
    }

    /**
     * 同步京东商品包下信息
     * @return
     */
    public Result  getJdPkgList(){
        //删除京东商品包
        jdGoodsMapper.deleteJdPackage();
        log.info("删除京东商品包成功！");
        JdClient client=new DefaultJdClient(config.getServerUrl(),config.getToken(),config.getApiKey(),config.getApiSecret());
        KeplerXuanpinGetpkglistRequest request=new KeplerXuanpinGetpkglistRequest();
        request.setMode(0);
        request.setVersion("1.0");
        request.setTimestamp(DateUtils.formatDate(new Date(),DateUtils.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
        KeplerXuanpinGetpkglistResponse response= null;
        try {
            response = client.execute(request);
            //获取京东商品包下的列表信息
            BasePkg[] list = response.getGetpkglistResult().getList();
            JdGoodsPackage jdGoodsPackage = new JdGoodsPackage();
            //遍历所有商品信息获取对应的属性
            for (int i = 0; i < list.length; i++) {
                //商品包描述
                jdGoodsPackage.setDesc(list[i].getDesc());
                //商品包id
                jdGoodsPackage.setId(Integer.parseInt(String.valueOf(list[i].getId())));
                //商品包名称
                jdGoodsPackage.setName(list[i].getName());
                //skuNum  每一个商品包下的所有商品总数
                jdGoodsPackage.setSkuNum(Integer.parseInt(String.valueOf(list[i].getSkuNum())));
                jdGoodsPackage.setUpdateTime(new Date());
                jdGoodsPackage.setCreateTime(new Date());
                jdGoodsMapper.save(jdGoodsPackage);
               // jdGoodsMapper.insert(jdGoodsPackage);
                log.info("保存京东商品包信息---->"+jdGoodsPackage.getId());
            }
        } catch (Exception e) {
            log.error("获取京东商品包失败--->"+e.getMessage());
        }
        return Result.success();
    }

    /**
     *  同步京东商品信息添加分页 判断
     * @param packageId
     * @param packageName
     * @param pageSize
     * @param pageNo
     */
    public  void selectJdGoodsByPackage(Long  packageId,String packageName,Integer pageSize,Integer pageNo){
        JdClient client=new DefaultJdClient(config.getServerUrl(),config.getToken(),config.getApiKey(),config.getApiSecret());
        KeplerXuanpinGetskuidlistRequest request=new KeplerXuanpinGetskuidlistRequest();
        request.setPkgId(packageId);
        request.setVersion("1.0");
        //根据skuNum判断每页的大小和pageNo
        request.setPageSize(pageSize);
        request.setPageNo(pageNo);
        try {
            KeplerXuanpinGetskuidlistResponse response=client.execute(request);
            //获取skuList
            long[] list = response.getGetskuidlistResult().getList();
            HashMap<String, Object> req= new HashMap<>(16);
            if(list.length>0){
                //获取每一个商品信息skuid
                //for (long l : list) {
                req.put("v","1.0");
                req.put("method","jd.kepler.service.promotion.goodsinfo");
                req.put("app_key",config.getApiKey());
                req.put("access_token",config.getToken());
                JSONObject jsonskuIds = new JSONObject();
                jsonskuIds.put("skuIds",JSONObject.toJSONString(list).toString().replace("[","").replace("]",""));
                //jsonskuIds.put("skuIds",String.valueOf(l));
                req.put("360buy_param_json",jsonskuIds.toJSONString());
                //构建时间戳
                String timestamp = DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                req.put("timestamp", timestamp);
                String sign = buildSign(timestamp, "1.0", "jd.kepler.service.promotion.goodsinfo", jsonskuIds.toJSONString(), config.getToken(), config.getApiKey(), config.getApiSecret());
                req.put("sign",sign);
                Map<String, Object> header = new HashMap<>(16);
                header.put("Content-Type", "application/x-www-form-urlencoded");
                String resp = null;
                try {
                    //一次获取
                    resp = HttpClientUtil.httpPostRequest1(config.getServerUrl(), header, req);
                } catch (Exception e) {
                    log.error("获取商品sku失败"+e.getMessage());
                }
                //保存京东商品信息
                saveJdGoods(resp,String.valueOf(packageId),packageName);
                // }
            }
        } catch (Exception e) {
            log.error("获取京东商品列表失败"+e.getMessage());
        }
    }

    /**
     * 保存商品信息
     * @param resp
     * @param packageId
     * @param packageName
     * @return
     * @throws Exception
     */
    public  Result saveJdGoods(String resp,String packageId,String packageName) throws Exception{
        String promotionGoodsinfoResponce = JSONObject.parseObject(resp).getString("jd_kepler_service_promotion_goodsinfo_responce");
        JdGoods jdGoods = new JdGoods();
        if(promotionGoodsinfoResponce!=null){
            //获取goodsinfoResult解析对象属性
            String goodsinfoResult = JSONObject.parseObject(promotionGoodsinfoResponce).getString("goodsinfoResult");
            JSONObject jsonObject = JSONObject.parseObject(goodsinfoResult);
            JSONArray result = jsonObject.getJSONArray("result");
            //遍历结果集
            for (int i = 0; i < result.size(); i++) {
                JSONObject jsonObject1 = result.getJSONObject(i);
                //设置商品包id
                jdGoods.setGoodsPackageId(Integer.parseInt(packageId));
                //设置商品包名称
                jdGoods.setGoodsPackageName(packageName);
                //设置skuid
                jdGoods.setSkuId(jsonObject1.getString("skuId"));
                //一级分类id
                jdGoods.setCid(jsonObject1.getInteger("cid"));
                //二级分类id
                jdGoods.setCid2(jsonObject1.getInteger("cid2"));
                //三级分类id
                jdGoods.setCid3(jsonObject1.getInteger("cid3"));
                jdGoods.setCidName(jsonObject1.getString("cidName"));
                jdGoods.setCid2Name(jsonObject1.getString("cid2Name"));
                jdGoods.setCid3Name(jsonObject1.getString("cid3Name"));
                //设置pc端的佣金比例
                jdGoods.setCommisionRatioPc(jsonObject1.getInteger("commisionRatioPc"));
                //设置无线端的佣金比例
                jdGoods.setCommisionRatioWl(jsonObject1.getInteger("commisionRatioWl"));
                //设置京东商品加
                jdGoods.setWlUnitPrice(jsonObject1.getBigDecimal("wlUnitPrice"));
                jdGoods.setUnitPrice(jsonObject1.getBigDecimal("unitPrice"));
                jdGoods.setGoodsName(jsonObject1.getString("goodsName"));
                jdGoods.setImgUrl(jsonObject1.getString("imgUrl"));
                //设置落地页链接
                jdGoods.setMaterialUrl(jsonObject1.getString("materialUrl"));
                jdGoods.setSource("京东");
                //获取商品的上下线状态
                Integer status = getJdGoodsStatus(Long.valueOf(jsonObject1.getString("skuId")));
                jdGoods.setStatus(status);
                //佣金=京东价*无线佣金比例
                BigDecimal commission = jsonObject1.getBigDecimal("unitPrice").multiply(new BigDecimal(jsonObject1.getInteger("commisionRatioWl")).divide(new BigDecimal(100)));
                jdGoods.setCommission(commission);
                //获取推广短连接
                String shortUlr = getPromotionUrl(jsonObject1.getString("materialUrl"));
                jdGoods.setShortUrl( shortUlr);
                //同步商品一个商品包下的商品信息
                try {
                    //再执行保存操作
                    jdGoods.setCreateTime(new Date());
                    jdGoods.setUpdateTime(new Date());
                    //jdGoodsMapper.saveJdGoods(jdGoods);
                    jdGoodsMapper.insert(jdGoods);
                    log.info("京东商品信息入库成功--->"+JSONObject.toJSONString(jdGoods.getSkuId()));
                } catch (NumberFormatException e) {
                    log.error("京东商品信息入库失败"+e.getMessage()+"--skuId--"+result.getJSONObject(i).getString("skuId")
                            +"--skuId--"+result.getJSONObject(i).getString("skuId")
                            +"--cid--"+result.getJSONObject(i).getInteger("cid")
                            +"--cid2--"+result.getJSONObject(i).getInteger("cid2")
                            +"--cid3--"+result.getJSONObject(i).getInteger("cid3")
                            +"--cidName--"+result.getJSONObject(i).getString("cidName")
                            +"--cidName2--"+result.getJSONObject(i).getString("cidName2")
                            +"--cidName3--"+result.getJSONObject(i).getString("cidName3")
                            +"--commisionRatioPc--"+result.getJSONObject(i).getInteger("commisionRatioPc")
                            +"--commisionRatioWl--"+result.getJSONObject(i).getInteger("commisionRatioWl")
                            +"--wlUnitPrice--"+result.getJSONObject(i).getBigDecimal("wlUnitPrice")
                            +"--unitPrice--"+result.getJSONObject(i).getBigDecimal("unitPrice")
                            +"--goodsName--"+result.getJSONObject(i).getString("goodsName")
                            +"--imgUrl--"+result.getJSONObject(i).getString("imgUrl")
                            +"--goodsName--"+result.getJSONObject(i).getString("goodsName")
                            +"--goodsName--"+result.getJSONObject(i).getString("goodsName")
                    );
                }
            }
        }
        return  Result.success();
    }

    /**
     * 获取sku 详情 获取商品上下线状态
     * @return
     */
    private    Integer getJdGoodsStatus(Long skuId){
        JdClient client=new DefaultJdClient(config.getServerUrl(),config.getToken(),config.getApiKey(),config.getApiSecret());
        ProductBaseQueryRequest request=new ProductBaseQueryRequest();
        request.setSku(skuId);
        request.setVersion("1.0");
        try {
            ProductBaseQueryResponse response=client.execute(request);
            Map<String, Object> result = response.getQueryResult().getResult();
            if(result.get("status")!=null){
                return Integer.parseInt((String) result.get("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取商品推广链接
     *
     * @return
     * @throws JdApiExcepiton
     */
    public String getPromotionUrl(String materalUrl) throws Exception {
        JdClient client = new DefaultJdClient(config.getServerUrl(), config.getToken(),
                config.getApiKey(), config.getApiSecret());
        KplOpenPromotionPidurlconvertRequest request = new KplOpenPromotionPidurlconvertRequest();
        request.setWebId(config.getUnionId().toString());
        request.setPositionId(0L);
        request.setMateralId(materalUrl);
        request.setSubUnionId("110");
        //传1表示返回短链接，传0表示返回长链接
        request.setShortUrl(1);
        //传1为联盟格式链接，默认不传为开普勒格式链接
        request.setKplClick(1);
        KplOpenPromotionPidurlconvertResponse response;
        try {
            response = client.execute(request);
            JSONObject responseJson = JSONObject.parseObject(response.getMsg());
            //获取推广链接的短连接
            String shortUrl = responseJson.getJSONObject("jd_kpl_open_promotion_pidurlconvert_responce")
                    .getJSONObject("pidurlconvertResult").getJSONObject("clickUrl").getString("shortUrl");
            return shortUrl;
        } catch (Exception e) {
            log.error("调用JD：获取推广商品信息接口（jd.kepler.service.promotion.goodsinfo）异常", e);
        }
        return null;
    }


    /**
     * 构建签名信息  使用京东原生方式调用接口
     * @param timestamp
     * @param version
     * @param method
     * @param paramJson
     * @param accessToken
     * @param appKey
     * @param appSecret
     * @return
     * @throws Exception
     */
    private static String buildSign(String timestamp, String version,
                                    String method , String paramJson , String accessToken ,String appKey, String appSecret)
            throws Exception {
        //第一步，按照顺序填充参数
        Map<String, String> map = new TreeMap();
        map.put("access_token", accessToken);
        map.put("app_key", appKey);
        map.put("method", method);
        map.put("timestamp", timestamp);
        map.put("v", version);
        map.put("360buy_param_json", paramJson);
        //签名
        String signToken = sign(map,appSecret);

        return signToken;
    }

    /**
     * 生成签名
     * @param pmap appSecret
     * @return
     */
    private static String sign(Map<String, String> pmap, String appSecret) throws Exception {
        StringBuilder sb = new StringBuilder(appSecret);
        Iterator var4 = pmap.entrySet().iterator();

        while(var4.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)var4.next();
            String name = (String)entry.getKey();
            String value = (String)entry.getValue();
            if (StringUtil.areNotEmpty(new String[]{name, value})) {
                sb.append(name).append(value);
            }
        }

        sb.append(appSecret);
        String result = CodecUtil.md5(sb.toString());
        return result;
    }
}
