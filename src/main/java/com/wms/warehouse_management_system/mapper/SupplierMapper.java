package com.wms.warehouse_management_system.mapper;

import com.wms.warehouse_management_system.dtos.SupplierItemResponseDto;
import com.wms.warehouse_management_system.dtos.SupplierResponseDto;
import com.wms.warehouse_management_system.entities.Supplier;
import com.wms.warehouse_management_system.entities.SupplierItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SupplierMapper {

    public SupplierResponseDto mapEntityToSupplierResponseDto(Supplier entity){
        SupplierResponseDto responseDto = new SupplierResponseDto();
        responseDto.setName(entity.getName());
        responseDto.setEmail(entity.getEmail());
        responseDto.setPhone(entity.getPhone());
        responseDto.setAddress1(entity.getAddress1());
        responseDto.setAddress2(entity.getAddress2());
        responseDto.setCity(entity.getCity());
        responseDto.setCountry(entity.getCountry());
        responseDto.setPostalCode(entity.getPostalCode());
        List<SupplierItemResponseDto> supplierItemList  = new ArrayList<>();
        if (entity.getSupplierItems() != null){
            supplierItemList = entity.getSupplierItems()
                    .stream()
                    .map(this::mapEntityToSupplierItemResponseDto)
                    .toList();
        }
        responseDto.setSupplierItems(supplierItemList);
        return responseDto;
    }

    public SupplierItemResponseDto mapEntityToSupplierItemResponseDto(SupplierItem entity){

    }
}
