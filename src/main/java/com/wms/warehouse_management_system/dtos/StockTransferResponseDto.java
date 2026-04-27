package com.wms.warehouse_management_system.dtos;

import com.wms.warehouse_management_system.enums.TransferStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StockTransferResponseDto {
    private Long id;

    private WarehouseResponseDto sourceWarehouse;

    private WarehouseResponseDto destinationWarehouse;

    private TransferStatus status;

    private List<StockTransferItemResponseDto> stockTransferItems = new ArrayList<>();
}
