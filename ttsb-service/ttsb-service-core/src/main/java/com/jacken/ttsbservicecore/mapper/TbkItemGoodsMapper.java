package com.jacken.ttsbservicecore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jacken.wqttsbmodel.entity.TbkItemGoods;
import com.jacken.wqttsbmodel.request.TbkGoodsRequest;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TbkItemGoodsMapper extends BaseMapper<TbkItemGoods> {
    /**
     * 分页查询
     * @param page
     * @param request
     * @return
     */
    List<Map<String, Object>> selectTbkGoodsListPage(IPage page, @Param("request") TbkGoodsRequest request);

    /**
     * 商品所有商品信息
     */
    @Delete("delete from tbk_item_goods")
    void deleteAllTbkGoods();
}