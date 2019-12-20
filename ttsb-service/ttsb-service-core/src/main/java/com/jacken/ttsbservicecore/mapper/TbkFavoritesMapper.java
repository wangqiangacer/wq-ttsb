package com.jacken.ttsbservicecore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jacken.wqttsbmodel.entity.TbkFavorites;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbkFavoritesMapper extends BaseMapper<TbkFavorites> {


    @Delete("delete from tbk_favorites")
    void deleteAllFavorites();
}