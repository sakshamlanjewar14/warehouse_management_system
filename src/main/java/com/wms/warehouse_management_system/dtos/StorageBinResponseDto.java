package com.wms.warehouse_management_system.dtos;

import com.wms.warehouse_management_system.entities.InventoryItem;
import com.wms.warehouse_management_system.entities.Warehouse;
import lombok.Data;

import java.util.List;

@Data
public class StorageBinResponseDto {
    private Long binId;
    private String binCode;
    private Integer totalCapacity;
    private Integer availableCapacity;
    private String warehouseName;
    private List<InventoryItem> inventoryItems;
}
