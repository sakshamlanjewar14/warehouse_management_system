package com.wms.warehouse_management_system.mapper;

import com.wms.warehouse_management_system.dtos.StockTransferItemRequestDto;
import com.wms.warehouse_management_system.dtos.StockTransferItemResponseDto;
import com.wms.warehouse_management_system.dtos.StockTransferRequestDto;
import com.wms.warehouse_management_system.dtos.StockTransferResponseDto;
import com.wms.warehouse_management_system.entities.Product;
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
        responseDto.setId(entity.getId());
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
        responseDto.setId(entity.getId());
        responseDto.setStockTransfer(entity.getStockTransfer());
        responseDto.setProduct(entity.getProduct());
        responseDto.setQuantity(entity.getQuantity());
        return responseDto;
    }

    //request for  dto to entity
    public StockTransfer mapRequestDtoToStockTransferEntity(StockTransferRequestDto requestDto){
        StockTransfer stockTransfer = new StockTransfer();
        stockTransfer.setSourceWarehouse(requestDto.getSourceWarehouse());
        stockTransfer.setDestinationWarehouse(requestDto.getDestinationWarehouse());
        stockTransfer.setStatus(requestDto.getStatus());
        List<StockTransferItem> stockTransferItemList = new ArrayList<>();
        if(requestDto.getStockTransferItems() != null){
            stockTransferItemList = requestDto.getStockTransferItems()
                    .stream()
                    .map(itm->this.mapRequestDtoToStockTransferItemEntity(itm,stockTransfer))
                    .toList();
        }
        stockTransfer.setStockTransferItems(stockTransferItemList);
        return stockTransfer;
    }

    public StockTransferItem mapRequestDtoToStockTransferItemEntity(StockTransferItemRequestDto requestDto,StockTransfer stockTransfer, Product product){
        StockTransferItem stockTransferItem = new StockTransferItem();
        stockTransferItem.setStockTransfer(stockTransfer);
        stockTransferItem.setProduct(product);
        stockTransferItem.setQuantity(requestDto.getQuantity());
        return  stockTransferItem;
    }


}
