package com.wms.warehouse_management_system.mapper;

import com.wms.warehouse_management_system.dtos.*;
import com.wms.warehouse_management_system.entities.Product;
import com.wms.warehouse_management_system.entities.StockTransfer;
import com.wms.warehouse_management_system.entities.StockTransferItem;
import com.wms.warehouse_management_system.entities.Warehouse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StockTransferMapper {
    private final WarehouseMapper warehouseMapper;
    private final ProductMapper productMapper;

    //Response for entity to dto
    public StockTransferResponseDto mapEntityToStockTransferResponseDto(StockTransfer entity){
        StockTransferResponseDto responseDto = new StockTransferResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setSourceWarehouse(warehouseMapper.mapEntityToWarehouseResponseDto(entity.getSourceWarehouse()));
        responseDto.setDestinationWarehouse(warehouseMapper.mapEntityToWarehouseResponseDto(entity.getDestinationWarehouse()));
        responseDto.setStatus(entity.getStatus());
        List<StockTransferItemResponseDto> stockTransferItemList = new ArrayList<>();
        if(entity.getStockTransferItems() != null){
            stockTransferItemList = entity.getStockTransferItems()
                    .stream()
                    .map(sti -> this.mapEntityToStockTransferItemResponseDto(sti, entity))
                    .toList();
        }
        responseDto.setStockTransferItems(stockTransferItemList);
        return responseDto;
    }

    public  StockTransferItemResponseDto mapEntityToStockTransferItemResponseDto(StockTransferItem entity, StockTransfer stockTransferEntity){
        StockTransferItemResponseDto responseDto = new StockTransferItemResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setStockTransferId(stockTransferEntity.getId());
        responseDto.setProduct(productMapper.mapEntityToProductResponseDto(entity.getProduct()));
        responseDto.setQuantity(entity.getQuantity());
        return responseDto;
    }

    //request for  dto to entity
    public StockTransfer mapRequestDtoToStockTransferEntity(StockTransferRequestDto requestDto,
                                                            Warehouse sourceW,
                                                            Warehouse destinationW){
        StockTransfer stockTransfer = new StockTransfer();
        stockTransfer.setSourceWarehouse(sourceW);
        stockTransfer.setDestinationWarehouse(destinationW);
        stockTransfer.setStatus(requestDto.getStatus());
        return stockTransfer;
    }

    public StockTransferItem mapRequestDtoToStockTransferItemEntity(StockTransferItemRequestDto requestDto,
                                                                    StockTransfer stockTransfer,
                                                                    Product product){
        StockTransferItem stockTransferItem = new StockTransferItem();
        stockTransferItem.setStockTransfer(stockTransfer);
        stockTransferItem.setProduct(product);
        stockTransferItem.setQuantity(requestDto.getQuantity());
        return  stockTransferItem;
    }


}
