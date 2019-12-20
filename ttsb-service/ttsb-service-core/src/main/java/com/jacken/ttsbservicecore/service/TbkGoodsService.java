package com.jacken.ttsbservicecore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jacken.wqttsbmodel.entity.TbkItemGoods;
import com.jacken.wqttsbmodel.request.TbkGoodsRequest;
import com.jacken.wqttsbmodel.result.Result;

public interface TbkGoodsService extends IService<TbkItemGoods> {
    /**
     * 同步淘宝客商品信息
     * @return
     */
    Result getTbkGoodsItem();

    Result selectTbkGoodsPage(TbkGoodsRequest request);
}
