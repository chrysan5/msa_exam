package com.sparta.msa_exam.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//order에서 product를 호출하는 코드에 쓰임
@FeignClient(name = "product-service")
public interface ProductClient {
    //상품 조회
    @GetMapping("/products/{id}")
    ProductResponseDto getProduct(@PathVariable("id") Long id);
}
