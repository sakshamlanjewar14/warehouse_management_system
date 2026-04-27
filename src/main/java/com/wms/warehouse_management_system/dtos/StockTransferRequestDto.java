package com.wms.warehouse_management_system.dtos;

import com.wms.warehouse_management_system.enums.TransferStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StockTransferRequestDto {

    private Long id;

    private Long sourceWarehouseId;

    private Long destinationWarehouseId;

    private TransferStatus status;

    private List<StockTransferItemRequestDto> stockTransferItems = new ArrayList<>();
}
