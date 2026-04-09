package com.wms.warehouse_management_system.dtos;

import lombok.Data;

@Data
public class SupplierItemResponseDto {

    private Long itemId;

    private String productName;

    private Double price;

    private Integer quantity;

    private Long productId;
}
