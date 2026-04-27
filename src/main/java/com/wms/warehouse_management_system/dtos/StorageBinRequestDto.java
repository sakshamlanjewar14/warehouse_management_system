package com.wms.warehouse_management_system.dtos;

import com.wms.warehouse_management_system.entities.InventoryItem;
import lombok.Data;

import java.util.List;

@Data
public class StorageBinRequestDto {

    private Long binId;

    private String binCode;

    private Integer totalCapacity;

    private Long warehouseId;

}
