package com.suhanlee.luckybikideffenceapiserver.shop.product.service;

import com.suhanlee.luckybikideffenceapiserver.config.error.ErrorCode;
import com.suhanlee.luckybikideffenceapiserver.config.error.exception.RestException;
import com.suhanlee.luckybikideffenceapiserver.shop.product.constants.ProductStatus;
import com.suhanlee.luckybikideffenceapiserver.shop.product.model.Product;
import com.suhanlee.luckybikideffenceapiserver.shop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    //상품 단일 조회
    public Product getProduct(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RestException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    //상품 리스트 조회
    public List<Product> getProductList(){
        return productRepository.findAllByStatus(ProductStatus.SALE);
    }
}
