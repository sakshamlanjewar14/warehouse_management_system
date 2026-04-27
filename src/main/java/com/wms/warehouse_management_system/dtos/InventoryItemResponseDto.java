package com.wms.warehouse_management_system.dtos;


import com.wms.warehouse_management_system.entities.Product;
import com.wms.warehouse_management_system.entities.StorageBin;
import lombok.Data;

@Data
public class InventoryItemResponseDto {

    private Long inventoryItemId;

    private Integer quantity;

    private String productName;

    private String storageBinCode;
}

