package com.wms.warehouse_management_system.mapper;

import com.wms.warehouse_management_system.dtos.InventoryItemResponseDto;
import com.wms.warehouse_management_system.dtos.StorageBinRequestDto;
import com.wms.warehouse_management_system.dtos.StorageBinResponseDto;
import com.wms.warehouse_management_system.entities.InventoryItem;
import com.wms.warehouse_management_system.entities.StorageBin;
import com.wms.warehouse_management_system.entities.Warehouse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StorageBinMapper {

    private final InventoryItemMapper inventoryItemMapper;

    //response for entity to dto
    public StorageBinResponseDto mapEntityToStorageBinResponseDto(StorageBin entity){
        StorageBinResponseDto responseDto = new StorageBinResponseDto();
        responseDto.setBinId(entity.getBinId());
        responseDto.setBinCode(entity.getBinCode());
        responseDto.setTotalCapacity(entity.getCapacity());

        int totalOccupiedCapacity = entity.getInventoryItems().stream().mapToInt(InventoryItem::getQuantity).sum();
        int availableBinCapacity = entity.getCapacity() - totalOccupiedCapacity;

        responseDto.setAvailableCapacity(availableBinCapacity);
        responseDto.setWarehouseName(entity.getWarehouse().getName());
        List<InventoryItemResponseDto> inventoryItemList = new ArrayList<>();
        if (entity.getInventoryItems() != null){
            inventoryItemList = entity.getInventoryItems()
                    .stream()
                    .map(inventoryItemMapper::mapEntityToInventoryItemResponseDto)
                    .toList();
        }
        responseDto.setInventoryItems(inventoryItemList);
        return responseDto;
    }


    //request for  dto to entity
    public StorageBin mapRequestDtoToStorageBinEntity(StorageBinRequestDto requestDto, Warehouse warehouse){
        StorageBin storageBin = new StorageBin();
        storageBin.setBinCode(requestDto.getBinCode());
        storageBin.setCapacity(requestDto.getTotalCapacity());
        storageBin.setWarehouse(warehouse);
        return storageBin;
    }
}
