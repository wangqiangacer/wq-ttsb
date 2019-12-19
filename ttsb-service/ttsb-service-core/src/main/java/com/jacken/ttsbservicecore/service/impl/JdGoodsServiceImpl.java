package com.jacken.ttsbservicecore.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jacken.ttsbservicecore.mapper.JdGoodsMapper;
import com.jacken.ttsbservicecore.result.Result;
import com.jacken.ttsbservicecore.service.JdGoodsService;
import com.jacken.wqttsbmodel.baseentity.PageModel;
import com.jacken.wqttsbmodel.entity.JdGoods;
import com.jacken.wqttsbmodel.request.JdGoodsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("ALL")
@Slf4j
public class JdGoodsServiceImpl implements JdGoodsService {

    @Resource
    JdGoodsMapper jdGoodsMapper;
    @Override
    public Result selectAllJdGoods() {
        return Result.success(jdGoodsMapper.selectList(null));
    }

    @Override
    public Result selectJdGoodsPage(JdGoodsRequest request) {
        IPage<Map<String, Object>> page = new Page<>(request.getCurrentPage(), request.getPageSize());
        List<Map<String, Object>> maps = jdGoodsMapper.selectCreditCountListPage(page, request);
        return Result.success(PageModel.buildByIPage(page.setRecords(maps)));
    }
}
