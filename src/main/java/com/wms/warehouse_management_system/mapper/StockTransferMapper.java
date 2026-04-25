package com.wms.warehouse_management_system.mapper;

import com.wms.warehouse_management_system.dtos.StockTransferItemResponseDto;
import com.wms.warehouse_management_system.dtos.StockTransferRequestDto;
import com.wms.warehouse_management_system.dtos.StockTransferResponseDto;
import com.wms.warehouse_management_system.entities.StockTransfer;
import com.wms.warehouse_management_system.entities.StockTransferItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StockTransferMapper {

    //Response for entity to dto
    public StockTransferResponseDto mapEntityToStockTransferResponseDto(StockTransferRequestDto entity){
        StockTransferResponseDto responseDto = new StockTransferResponseDto();
        responseDto.setSourceWarehouse(entity.getSourceWarehouse());
        responseDto.setDestinationWarehouse(entity.getDestinationWarehouse());
        responseDto.setStatus(entity.getStatus());
        List<StockTransferItemResponseDto> stockTransferItemList = new ArrayList<>();
        if(entity.getStockTransferItems() != null){
            stockTransferItemList = entity.getStockTransferItems()
                    .stream()
                    .map(this::mapEntityToStockTransferItemResponseDto)
                    .toList();
        }
        responseDto.setStockTransferItems(stockTransferItemList);
        return responseDto;
    }

    public  StockTransferItemResponseDto mapEntityToStockTransferItemResponseDto(StockTransferItem entity){
        StockTransferItemResponseDto responseDto = new StockTransferItemResponseDto();
        responseDto.setStockTransfer(entity.);
    }





}
