package com.order_service.order_service.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderLineItemsDto {
    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
