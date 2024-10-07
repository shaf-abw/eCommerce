package com.abw.ecommerce.SearchService.repository;

import com.abw.ecommerce.SearchService.document.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, Long> {
}
