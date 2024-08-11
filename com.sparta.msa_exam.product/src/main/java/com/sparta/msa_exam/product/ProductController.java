package com.sparta.msa_exam.product;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    @Value("${server.port}") // 애플리케이션이 실행 중인 포트를 주입받습니다.
    private String serverPort;


    private final ProductService productService;


    @PostMapping
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto productRequestDto) {
        return productService.createProduct(productRequestDto);
    }

    @GetMapping
    public Page<ProductResponseDto> getProducts(
            @RequestParam("page") int page, //프론트로부터 받아오는 page 값은 1부터시작
            @RequestParam("size") int size, //size : 한 페이지에 보여줄 상품 개수
            @RequestParam("sortBy") String sortBy, //정렬항목(ex-id, title, lprice, createdAt)
            @RequestParam("isAsc") boolean isAsc //true일경우 오름차순, false인 경우 내림차순
           ) {
        return productService.getProducts(page-1, size, sortBy, isAsc);
    }

    @GetMapping("/{id}")
    public Long getProduct(@PathVariable("id") Long id){
        return id;
    }
}
