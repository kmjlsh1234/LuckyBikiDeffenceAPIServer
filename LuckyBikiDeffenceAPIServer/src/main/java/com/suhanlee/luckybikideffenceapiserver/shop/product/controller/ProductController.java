package com.suhanlee.luckybikideffenceapiserver.shop.product.controller;

import com.suhanlee.luckybikideffenceapiserver.shop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    //상품 단일 조회
    @GetMapping("/v1/products/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable("productId") long productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    //상품 리스트 조회
    @GetMapping("/v1/products")
    public ResponseEntity<?> getProductList(){
        return ResponseEntity.ok(productService.getProductList());
    }
}
