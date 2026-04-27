package com.wms.warehouse_management_system.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wms.warehouse_management_system.entities.Product;
import com.wms.warehouse_management_system.entities.StorageBin;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.List;

@Data
public class InventoryItemRequestDto {

    private Long inventoryItemId;

    private Integer quantity;

    private Long productId;

    private Long storageBinId;
}
