package com.wms.warehouse_management_system.dtos;

import com.wms.warehouse_management_system.entities.StorageBin;
import lombok.Data;

import java.util.List;

@Data
public class WarehouseRequestDto {
    private String name;
    private String location;
    private Integer capacity;
    private List<StorageBin> storageBins;
}
