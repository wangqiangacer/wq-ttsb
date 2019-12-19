package com.jacken.ttsbservicecore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jacken.wqttsbmodel.entity.JdGoods;
import com.jacken.wqttsbmodel.request.JdGoodsRequest;
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
}