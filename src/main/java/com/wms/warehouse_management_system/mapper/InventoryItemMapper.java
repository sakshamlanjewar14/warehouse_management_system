package com.wms.warehouse_management_system.mapper;

import com.wms.warehouse_management_system.dtos.InventoryItemRequestDto;
import com.wms.warehouse_management_system.dtos.InventoryItemResponseDto;
import com.wms.warehouse_management_system.entities.InventoryItem;
import com.wms.warehouse_management_system.entities.Product;
import com.wms.warehouse_management_system.entities.StorageBin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryItemMapper {

    //response for entity to dto
    public InventoryItemResponseDto mapEntityToInventoryItemResponseDto(InventoryItem entity){
        InventoryItemResponseDto responseDto = new InventoryItemResponseDto();
        responseDto.setInventoryItemId(entity.getInventoryItemId());
        responseDto.setQuantity(entity.getQuantity());
        responseDto.setProductName(entity.getProduct().getName());
        responseDto.setStorageBinCode(entity.getStorageBin().getBinCode());
        return responseDto;
    }


    //request for  dto to entity
    public InventoryItem mapRequestDtoToInventoryItemEntity(InventoryItemRequestDto requestDto,
                                                            Product product, StorageBin storageBin){
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setInventoryItemId(requestDto.getInventoryItemId());
        inventoryItem.setQuantity(requestDto.getQuantity());
        inventoryItem.setProduct(product);
        inventoryItem.setStorageBin(storageBin);
        return inventoryItem;
    }

}
