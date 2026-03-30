package com.wms.warehouse_management_system.dtos;

import com.wms.warehouse_management_system.entities.Product;
import lombok.Data;

import java.util.List;

@Data
public class InventoryItemRequestDto {
    private Long productId;
    List<InventoryItemQuantityRequestDto> rows;
}
