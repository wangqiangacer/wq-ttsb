package com.jacken.ttsbcodegenerator.dao;

import com.jacken.ttsbcodegenerator.entity.TbkFavorites;
import java.util.List;

public interface TbkFavoritesMapper {
    int insert(TbkFavorites record);

    List<TbkFavorites> selectAll();
}