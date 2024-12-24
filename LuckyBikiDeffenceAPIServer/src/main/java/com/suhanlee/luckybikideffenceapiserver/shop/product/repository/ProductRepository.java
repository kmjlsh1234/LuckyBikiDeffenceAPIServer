package com.suhanlee.luckybikideffenceapiserver.shop.product.repository;

import com.suhanlee.luckybikideffenceapiserver.shop.product.constants.ProductStatus;
import com.suhanlee.luckybikideffenceapiserver.shop.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByStatus(ProductStatus status);
}
