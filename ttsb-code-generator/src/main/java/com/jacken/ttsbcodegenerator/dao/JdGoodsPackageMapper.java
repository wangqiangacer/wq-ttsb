package com.jacken.ttsbcodegenerator.dao;

import com.jacken.ttsbcodegenerator.entity.JdGoodsPackage;
import java.util.List;

public interface JdGoodsPackageMapper {
    int insert(JdGoodsPackage record);

    List<JdGoodsPackage> selectAll();
}