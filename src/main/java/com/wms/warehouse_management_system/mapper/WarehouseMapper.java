package com.wms.warehouse_management_system.mapper;

import com.wms.warehouse_management_system.dtos.StorageBinResponseDto;
import com.wms.warehouse_management_system.dtos.WarehouseRequestDto;
import com.wms.warehouse_management_system.dtos.WarehouseResponseDto;
import com.wms.warehouse_management_system.entities.StorageBin;
import com.wms.warehouse_management_system.entities.Warehouse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WarehouseMapper {
    final private StorageBinMapper storageBinMapper;

//response for entity to dto
    public WarehouseResponseDto mapEntityToWarehouseResponseDto(Warehouse entity){
        WarehouseResponseDto responseDto = new WarehouseResponseDto();
        responseDto.setWarehouseId(entity.getWarehouseId());
        responseDto.setName(entity.getName());
        responseDto.setCapacity(entity.getCapacity());
        responseDto.setLocation(entity.getLocation());
        List<StorageBinResponseDto> storageBinList = new ArrayList<>();
        if (entity.getStorageBins() != null){
            storageBinList = entity.getStorageBins()
                    .stream()
                    .map(storageBinMapper::mapEntityToStorageBinResponseDto)
                    .toList();
        }
        responseDto.setStorageBins(storageBinList);
        return responseDto;
    }


    //request for  dto to entity
    public Warehouse mapRequestDtoToWarehouseEntity(WarehouseRequestDto requestDto){
        Warehouse warehouse = new Warehouse();
        warehouse.setName(requestDto.getName());
        warehouse.setLocation(requestDto.getLocation());
        warehouse.setCapacity(requestDto.getCapacity());
        return warehouse;
    }
}
