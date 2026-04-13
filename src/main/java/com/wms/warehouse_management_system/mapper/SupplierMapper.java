package com.wms.warehouse_management_system.mapper;

import com.wms.warehouse_management_system.dtos.SupplierItemRequestDto;
import com.wms.warehouse_management_system.dtos.SupplierItemResponseDto;
import com.wms.warehouse_management_system.dtos.SupplierRequestDto;
import com.wms.warehouse_management_system.dtos.SupplierResponseDto;
import com.wms.warehouse_management_system.entities.Supplier;
import com.wms.warehouse_management_system.entities.SupplierItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SupplierMapper {

    //response for entity to dto
    public SupplierResponseDto mapEntityToSupplierResponseDto(Supplier entity){
        SupplierResponseDto responseDto = new SupplierResponseDto();
        responseDto.setSupplierId(entity.getSupplierId());
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
        SupplierItemResponseDto responseDto = new SupplierItemResponseDto();
        responseDto.setItemId(entity.getItemId());
        responseDto.setPrice(entity.getPrice());
        responseDto.setQuantity(entity.getQuantity());
        responseDto.setProductName(entity.getProductName());
        responseDto.setProductId(entity.getProductId());
        return responseDto;
    }


    //request for  dto to entity
    public Supplier mapRequestDtoToSupplierEntity(SupplierRequestDto requestDto){
        Supplier supplier = new Supplier();
        supplier.setSupplierId(requestDto.getSupplierId());
        supplier.setName(requestDto.getName());
        supplier.setSupplierCode(requestDto.getSupplierCode());
        supplier.setEmail(requestDto.getEmail());
        supplier.setPhone(requestDto.getPhone());
        supplier.setAddress1(requestDto.getAddress1());
        supplier.setAddress2(requestDto.getAddress2());
        supplier.setCity(requestDto.getCity());
        supplier.setState(requestDto.getState());
        supplier.setCountry(requestDto.getCountry());
        supplier.setPostalCode(requestDto.getPostalCode());
        List<SupplierItem> supplierItemList = new ArrayList<>();
        if (requestDto.getSupplierItems() != null){
            supplierItemList = requestDto.getSupplierItems()
                    .stream()
                    .map(itm ->this.mapRequestDtoToSupplierItemEntity(itm, supplier))
                    .toList();
        }
        supplier.setSupplierItems(supplierItemList);
        return supplier;
    }


    public SupplierItem mapRequestDtoToSupplierItemEntity(SupplierItemRequestDto requestDto, Supplier supplier){
        SupplierItem supplierItem = new SupplierItem();
        //supplierItem.setItemId(requestDto.getItemId());
        supplierItem.setProductName(requestDto.getProductName());
        supplierItem.setPrice(requestDto.getPrice());
        supplierItem.setQuantity(requestDto.getQuantity());
        supplierItem.setProductId(requestDto.getProductId());
        supplierItem.setSupplier(supplier);
        return supplierItem;
    }
}
