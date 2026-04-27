package com.wms.warehouse_management_system.dtos;


import com.wms.warehouse_management_system.entities.InventoryItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductResponseDto {
    private Long productId;
    private String name;
    private String sku;
    private String description;
    private String barcode;
    private BigDecimal price;
    private Double weight;
    private String imageUrl;
    private List<InventoryItemResponseDto> inventoryItems;
}
