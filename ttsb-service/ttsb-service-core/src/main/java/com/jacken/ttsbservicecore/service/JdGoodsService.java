package com.jacken.ttsbservicecore.service;
import com.jacken.ttsbservicecore.result.Result;
import com.jacken.wqttsbmodel.request.JdGoodsRequest;


public interface JdGoodsService {

    /**
     * 查询所有京东商品
     * @return
     */
    Result selectAllJdGoods();

    /**
     * 分页处理
     * @param request
     * @return
     */

    Result selectJdGoodsPage(JdGoodsRequest request);

}
