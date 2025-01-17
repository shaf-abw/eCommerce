package com.abw.ecommerce.FavouriteService.service.impl;

import com.abw.ecommerce.FavouriteService.constant.ConfigConstant;
import com.abw.ecommerce.FavouriteService.dto.FavouriteDto;
import com.abw.ecommerce.FavouriteService.dto.ProductDto;
import com.abw.ecommerce.FavouriteService.dto.UserDto;
import com.abw.ecommerce.FavouriteService.entity.id.FavouriteId;
import com.abw.ecommerce.FavouriteService.exception.wrapper.FavouriteNotFoundException;
import com.abw.ecommerce.FavouriteService.helper.FavouriteMappingHelper;
import com.abw.ecommerce.FavouriteService.repository.FavouriteRepository;
import com.abw.ecommerce.FavouriteService.service.FavouriteService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Transactional
@Slf4j
public class FavouriteServiceImpl implements FavouriteService {

    private final FavouriteRepository favouriteRepository;
    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;

    @Autowired
    public FavouriteServiceImpl(FavouriteRepository favouriteRepository, RestTemplate restTemplate, ModelMapper modelMapper) {
        this.favouriteRepository = favouriteRepository;
        this.restTemplate = restTemplate;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<FavouriteDto> findAll() {
        return favouriteRepository.findAll()
                .stream()
                .map(FavouriteMappingHelper::map) // map(FavouriteMappingHelper::map)
                .peek(f -> {
                    f.setUserDto(restTemplate.getForObject(
                                    ConfigConstant.DiscoveredDomainsApi.USER_SERVICE_API_URL + "/" + f.getUserId(),
                                    UserDto.class)
                    );
                    f.setProductDto(restTemplate.getForObject(
                                    ConfigConstant.DiscoveredDomainsApi.PRODUCT_SERVICE_API_URL + "/" + f.getProductId(),
                                    ProductDto.class)
                    );
                })
                .distinct()
                .toList();
    }

    @Override
    public FavouriteDto findById(final FavouriteId favouriteId) {
        return this.favouriteRepository.findById(favouriteId)
                .map(FavouriteMappingHelper::map)
                .map(f -> {
                    f.setUserDto(this.restTemplate
                            .getForObject(ConfigConstant.DiscoveredDomainsApi
                                    .USER_SERVICE_API_URL + "/" + f.getUserId(), UserDto.class));
                    f.setProductDto(this.restTemplate
                            .getForObject(ConfigConstant.DiscoveredDomainsApi
                                    .PRODUCT_SERVICE_API_URL + "/" + f.getProductId(), ProductDto.class));
                    return f;
                })
                .orElseThrow(() -> new FavouriteNotFoundException(
                        String.format("Favourite with id: [%s] not found!", favouriteId)));
    }

    @Override
    public FavouriteDto save(final FavouriteDto favouriteDto) {
        return FavouriteMappingHelper.map(this.favouriteRepository
                .save(FavouriteMappingHelper.map(favouriteDto)));
    }

    @Override
    public FavouriteDto update(final FavouriteDto favouriteDto) {
        return FavouriteMappingHelper.map(this.favouriteRepository
                .save(FavouriteMappingHelper.map(favouriteDto)));
    }

    @Override
    public void deleteById(final FavouriteId favouriteId) {
        this.favouriteRepository.deleteById(favouriteId);
    }

}