package com.wms.warehouse_management_system.dtos;

import lombok.Data;

@Data
public class InventoryItemQuantityRequestDto {
    private Long storageBinId;
    private Integer quantity;
}
