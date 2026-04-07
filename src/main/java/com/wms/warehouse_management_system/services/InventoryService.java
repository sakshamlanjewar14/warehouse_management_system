package com.wms.warehouse_management_system.services;

import com.wms.warehouse_management_system.dtos.InventoryItemQuantityRequestDto;
import com.wms.warehouse_management_system.dtos.InventoryItemRequestDto;
import com.wms.warehouse_management_system.dtos.InventoryItemResponseDto;
import com.wms.warehouse_management_system.entities.InventoryItem;
import com.wms.warehouse_management_system.entities.Product;
import com.wms.warehouse_management_system.entities.StorageBin;
import com.wms.warehouse_management_system.repositorys.InventoryItemRepository;
import com.wms.warehouse_management_system.repositorys.ProductRepository;
import com.wms.warehouse_management_system.repositorys.StorageBinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryItemRepository inventoryItemRepository;
    private final ProductRepository productRepository;
    private final StorageBinRepository storageBinRepository;


//    Create inventory
@Transactional
    public List<InventoryItemResponseDto> createInventory(InventoryItemRequestDto inventoryItemRequestDto){
        Product product = productRepository.findById(inventoryItemRequestDto.getProductId()).orElse(null);
        List<InventoryItemResponseDto> inventoryItemResponseDtos = new ArrayList<>();
        if(product != null){
            for(InventoryItemQuantityRequestDto item : inventoryItemRequestDto.getRows()){
               StorageBin storageBin = storageBinRepository.findById(item.getStorageBinId()).orElse(null);
               if(storageBin != null){
                   Integer totalOccupiedCapacity = storageBin.getInventoryItems().stream().mapToInt(InventoryItem::getQuantity).sum();
                   Integer availableBinCapacity = storageBin.getCapacity() - totalOccupiedCapacity;
                   if(item.getQuantity().intValue() <= availableBinCapacity.intValue()){
                       InventoryItem inventoryItem = new InventoryItem();
                       inventoryItem.setQuantity(item.getQuantity());
                       inventoryItem.setProduct(product);
                       inventoryItem.setStorageBin(storageBin);
                       InventoryItem savedInventoryItem =  inventoryItemRepository.save(inventoryItem);
                       inventoryItemResponseDtos.add(savedInventoryItem.toResponseDto());
                   }else{
                       throw new RuntimeException("Capacity not available for the quqntity.");
                   }
               }else{
                   throw new RuntimeException("Bin not found.");
               }
            }
        }else{
            throw new RuntimeException("Product not found.");
        }
        return inventoryItemResponseDtos;
    }

//    Get all inventory
@Transactional
    public List<InventoryItemResponseDto> getAllInventory(){
        return inventoryItemRepository.findAll().stream().map(InventoryItem::toResponseDto).toList();
    }

//    Get inventory by id
@Transactional
    public InventoryItem getInventoryById(Long id){
        return inventoryItemRepository.findById(id).orElse(null);
    }

//    Update quantity
@Transactional
    public InventoryItem updateInventory(Long inventoryItemId, InventoryItem inventoryItemDetails){
        if(!Objects.equals(inventoryItemId, inventoryItemDetails )){
            return  null;
        }
        InventoryItem item = inventoryItemRepository.findById(inventoryItemId).orElse(null);
        if(item != null){
            item.setQuantity(inventoryItemDetails.getQuantity());
            return inventoryItemRepository.save(item);
        }
        return null;
    }

//    Delete inventory by id
@Transactional
    public void deleteInventory(Long id){
        inventoryItemRepository.deleteById(id);
    }
}
