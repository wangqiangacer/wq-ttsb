package com.jacken.ttsbcodegenerator.dao;

import com.jacken.ttsbcodegenerator.entity.TbkItemGoods;
import java.util.List;

public interface TbkItemGoodsMapper {
    int insert(TbkItemGoods record);

    List<TbkItemGoods> selectAll();
}