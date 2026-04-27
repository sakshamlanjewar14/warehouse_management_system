package com.wms.warehouse_management_system.mapper;

import com.wms.warehouse_management_system.dtos.InventoryItemResponseDto;
import com.wms.warehouse_management_system.dtos.ProductRequestDto;
import com.wms.warehouse_management_system.dtos.ProductResponseDto;
import com.wms.warehouse_management_system.entities.InventoryItem;
import com.wms.warehouse_management_system.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final InventoryItemMapper inventoryItemMapper;

    //response for entity to dto
    public ProductResponseDto mapEntityToProductResponseDto(Product entity){
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setProductId(entity.getProductId());
        responseDto.setName(entity.getName());
        responseDto.setPrice(entity.getPrice());
        responseDto.setDescription(entity.getDescription());
        responseDto.setBarcode(entity.getBarcode());
        responseDto.setSku(entity.getSku());
        responseDto.setWeight(entity.getWeight());
        responseDto.setImageUrl(entity.getImageUrl());
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
    public Product mapRequestDtoToProductEntity(ProductRequestDto requestDto){
        Product product = new Product();
        product.setProductId(requestDto.getProductId());
        product.setName(requestDto.getName());
        product.setSku(requestDto.getSku());
        product.setDescription(requestDto.getDescription());
        product.setBarcode(requestDto.getBarcode());
        product.setPrice(requestDto.getPrice());
        product.setWeight(requestDto.getWeight());
        product.setImageUrl(requestDto.getImageUrl());
        return product;
    }
}
