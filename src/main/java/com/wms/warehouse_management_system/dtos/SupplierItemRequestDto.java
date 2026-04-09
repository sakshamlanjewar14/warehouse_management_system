package com.wms.warehouse_management_system.dtos;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class SupplierItemRequestDto {

    private String productName;

    private Double price;

    private Integer quantity;

    private Long productId;
}
