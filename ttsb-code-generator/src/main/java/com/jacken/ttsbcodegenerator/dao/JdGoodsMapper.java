package com.jacken.ttsbcodegenerator.dao;

import com.jacken.ttsbcodegenerator.entity.JdGoods;
import java.util.List;

public interface JdGoodsMapper {
    int insert(JdGoods record);

    List<JdGoods> selectAll();
}