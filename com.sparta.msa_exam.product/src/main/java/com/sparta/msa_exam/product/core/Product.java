package com.sparta.msa_exam.product.core;



import com.sparta.msa_exam.product.ProductRequestDto;
import com.sparta.msa_exam.product.ProductResponseDto;
import jakarta.persistence.*;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED) //외부에서 생성못하게 설정
@Builder(access = AccessLevel.PRIVATE) //외부에서 생성못하게 설정
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer price;


    public static Product createProduct(ProductRequestDto requestDto) {
        return Product.builder()
                .name(requestDto.getName())
                .price(requestDto.getPrice())
                .build();
    }

    //put 메서드는 엔티티의 모든 정보를 통으로 바꿔줌
    //부분부분 바꾸는 건 patch, 통으로 바꾸는 건 put
    public void updateProduct(String name, String description, Integer price, Integer quantity, String updatedBy) {
        this.name = name;
        this.price = price;
    }

    // DTO로 변환하는 메서드
    public ProductResponseDto toResponseDto() {
        return new ProductResponseDto(
                this.id,
                this.name,
                this.price
        );
    }
}