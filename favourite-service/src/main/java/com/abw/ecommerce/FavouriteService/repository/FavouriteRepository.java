package com.abw.ecommerce.FavouriteService.repository;

import com.abw.ecommerce.FavouriteService.entity.Favourite;
import com.abw.ecommerce.FavouriteService.entity.id.FavouriteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FavouriteRepository extends JpaRepository<Favourite, FavouriteId> {

}
