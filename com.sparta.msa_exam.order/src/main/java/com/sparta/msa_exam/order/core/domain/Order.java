package com.sparta.msa_exam.order.core.domain;


import com.sparta.msa_exam.order.OrderResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "orders")
public class Order  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "order_item_id")
    private List<Long> orderItemIds;


    // 팩토리 메서드
    public static Order createOrder(List<Long> orderItemIds) {
        return Order.builder()
                .orderItemIds(orderItemIds)
                .build();

    }

    // DTO로 변환하는 메서드
    public OrderResponseDto toResponseDto() {
        return new OrderResponseDto(
                this.id,
                this.orderItemIds
        );
    }

    // 업데이트 메서드
    public void updateOrder(List<Long> orderItemIds) {
        this.orderItemIds = orderItemIds;
    }

}
