package com.jacken.ttsbservicecore.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jacken.wqttsbmodel.entity.JdGoods;
import com.jacken.wqttsbmodel.request.JdGoodsRequest;
import com.jacken.wqttsbmodel.result.Result;


public interface JdGoodsService extends IService<JdGoods> {

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

    /**
     * 同步京东商品
     * @return
     */
    Result getJdGoodsItem();

}
