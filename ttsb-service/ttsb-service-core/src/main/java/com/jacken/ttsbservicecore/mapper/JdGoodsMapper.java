package com.jacken.ttsbservicecore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jacken.wqttsbmodel.entity.JdGoods;
import com.jacken.wqttsbmodel.entity.JdGoodsPackage;
import com.jacken.wqttsbmodel.request.JdGoodsRequest;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface JdGoodsMapper extends BaseMapper<JdGoods> {
    /**
     * 分页查询
     * @param page
     * @param request
     * @return
     */
    List<Map<String, Object>> selectCreditCountListPage(IPage page, @Param("request") JdGoodsRequest request);

    /**
     * 删除所有商品信息
     */
    @Delete("delete from jd_goods")
    void deleteAllJdGoods();

    /**
     * 删除京东商品包
     */
    @Delete("delete from jd_goods_package")
    void deleteJdPackage();

    /**
     * 保存商品包
     * @param jdGoodsPackage
     */
    @Insert("insert into jd_goods_package(`id`,`name`,`skuNum`,`desc`,`create_time`,`update_time`) values(#{id},#{name},#{skuNum},#{desc},#{createTime},#{updateTime})")
    void  save(JdGoodsPackage jdGoodsPackage);

    /**
     * 保存京东商品信息 原生方式
     */
    @Insert("insert into jd_goods(`skuId`,`cid`,`cid2`,`cid3`" +
            ",`goods_name`,`cid_name`,`cid2_name`,`cid3_name`,`img_url`,`material_url`,`commision_ratio_pc`" +
            ",`commision_ratio_wl`,`wl_unit_price`,`unit_price`,`goods_package_id`,`goods_package_name`" +
            ",`create_time`,`update_time`,`short_url`,`source`,`commission`,`status`) values(#{skuId},#{cid},#{cid2},#{cid3},#{goodsName},#{cidName}" +
            ",#{cid2Name},#{cid3Name},#{imgUrl},#{materialUrl},#{commisionRatioPc},#{commisionRatioWl},#{wlUnitPrice}," +
            "#{unitPrice},#{goodsPackageId},#{goodsPackageName},#{createTime},#{updateTime},#{shortUrl},#{source},#{commission},#{status})")
    void saveJdGoods(JdGoods jdGoods);
}