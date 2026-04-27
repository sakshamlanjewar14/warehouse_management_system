package com.wms.warehouse_management_system.dtos;

import com.wms.warehouse_management_system.entities.Product;
import com.wms.warehouse_management_system.entities.StockTransfer;
import lombok.Data;

@Data
public class StockTransferItemRequestDto {

    private Long id;

    private Long stockTransferId;

    private Long productId;

    private Integer quantity;
}
