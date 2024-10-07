package com.abw.ecommerce.FavouriteService.service;

import com.abw.ecommerce.FavouriteService.dto.FavouriteDto;
import com.abw.ecommerce.FavouriteService.entity.id.FavouriteId;

import java.util.List;

public interface FavouriteService {
    List<FavouriteDto> findAll();
    FavouriteDto findById(final FavouriteId favouriteId);
    FavouriteDto save(final FavouriteDto favouriteDto);
    FavouriteDto update(final FavouriteDto favouriteDto);
    void deleteById(final FavouriteId favouriteId);
}
