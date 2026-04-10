package com.wms.warehouse_management_system.services;

import com.wms.warehouse_management_system.dtos.SupplierItemRequestDto;
import com.wms.warehouse_management_system.dtos.SupplierItemResponseDto;
import com.wms.warehouse_management_system.entities.SupplierItem;
import com.wms.warehouse_management_system.mapper.SupplierMapper;
import com.wms.warehouse_management_system.repositorys.SupplierItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SupplierItemService {

    @Autowired
    private final SupplierItemRepository supplierItemRepository;
    private final SupplierMapper supplierMapper;

    @Transactional
    public SupplierItemResponseDto createItem(SupplierItemRequestDto requestDto){
        SupplierItem supplierItem = supplierMapper.mapRequestDtoToSupplierItemEntity(requestDto, null);
        SupplierItem savedItem = supplierItemRepository.save(supplierItem);
        return supplierMapper.mapEntityToSupplierItemResponseDto(savedItem);
    }

    @Transactional(readOnly = true)
    public List<SupplierItemResponseDto> getAllItem(){
        List<SupplierItem> supplierItems = supplierItemRepository.findAll();
        return supplierItems.stream().map(supplierMapper::mapEntityToSupplierItemResponseDto).toList();
    }

    @Transactional(readOnly = true)
    public SupplierItemResponseDto getItemById(Long itemId){
        SupplierItem supplierItem = supplierItemRepository.findById(itemId).orElse(null);
        if (supplierItem != null){
            return supplierMapper.mapEntityToSupplierItemResponseDto(supplierItem);
        }
        return null;
    }

    @Transactional
    public void deleteItem(Long itemId){
        supplierItemRepository.deleteById(itemId);
    }

    @Transactional
    public SupplierItemResponseDto updateItem(Long itemId, SupplierItemRequestDto requestDto){
        if(!Objects.equals(itemId, requestDto.getItemId())){
            return null;
        }
        SupplierItem item = supplierItemRepository.findById(itemId).orElse(null);

        if(item != null){
            item.setProductName(requestDto.getProductName());
            item.setPrice(requestDto.getPrice());
            item.setQuantity(requestDto.getQuantity());
            item.setProductId(requestDto.getProductId());
            SupplierItem updatedItem = supplierItemRepository.save(item);
            return supplierMapper.mapEntityToSupplierItemResponseDto(updatedItem);
        }
        return null;
    }



}
