package com.wms.warehouse_management_system.services;

import com.wms.warehouse_management_system.dtos.InventoryItemRequestDto;
import com.wms.warehouse_management_system.dtos.InventoryItemResponseDto;
import com.wms.warehouse_management_system.entities.InventoryItem;
import com.wms.warehouse_management_system.entities.Product;
import com.wms.warehouse_management_system.entities.StorageBin;
import com.wms.warehouse_management_system.mapper.InventoryItemMapper;
import com.wms.warehouse_management_system.repositorys.InventoryItemRepository;
import com.wms.warehouse_management_system.repositorys.ProductRepository;
import com.wms.warehouse_management_system.repositorys.StorageBinRepository;
import lombok.RequiredArgsConstructor;
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
    private final InventoryItemMapper inventoryItemMapper;


    //    Create inventory
    @Transactional
    public List<InventoryItemResponseDto> createInventory(List<InventoryItemRequestDto> inventoryItemRequestDtos) {
        List<InventoryItemResponseDto> savedInventoryItemResponseDtos = new ArrayList<>();
        inventoryItemRequestDtos.forEach(ii->{
            Product product = productRepository.findById(ii.getProductId()).orElse(null);
            if (product != null) {
                StorageBin storageBin = storageBinRepository.findById(ii.getStorageBinId()).orElse(null);
                if (storageBin != null) {
                    int totalOccupiedCapacity = storageBin.getInventoryItems().stream().mapToInt(InventoryItem::getQuantity).sum();
                    int availableBinCapacity = storageBin.getCapacity() - totalOccupiedCapacity;
                    if (ii.getQuantity() <= availableBinCapacity) {
                        InventoryItem inventoryItem = new InventoryItem();
                        inventoryItem.setQuantity(ii.getQuantity());
                        inventoryItem.setProduct(product);
                        inventoryItem.setStorageBin(storageBin);
                        InventoryItem savedInventoryItem = inventoryItemRepository.save(inventoryItem);
                        savedInventoryItemResponseDtos.add(inventoryItemMapper.mapEntityToInventoryItemResponseDto(savedInventoryItem));
                    } else {
                        throw new RuntimeException("Capacity not available for the quantity.");
                    }
                }
            }
        });

        return savedInventoryItemResponseDtos;
    }

    //    Get all inventory
    @Transactional
    public List<InventoryItemResponseDto> getAllInventory() {
        return inventoryItemRepository.findAll().stream().map(inventoryItemMapper::mapEntityToInventoryItemResponseDto).toList();
    }

    //    Get inventory by id
    @Transactional
    public InventoryItemResponseDto getInventoryById(Long id) {
        InventoryItem inventoryItem = inventoryItemRepository.findById(id).orElse(null);
        return inventoryItemMapper.mapEntityToInventoryItemResponseDto(inventoryItem);
    }

    //    Update quantity
    @Transactional
    public InventoryItemResponseDto updateInventory(Long inventoryItemId, InventoryItem inventoryItemDetails) {
        if (!Objects.equals(inventoryItemId, inventoryItemDetails)) {
            return null;
        }
        InventoryItem item = inventoryItemRepository.findById(inventoryItemId).orElse(null);
        if (item != null) {
            item.setQuantity(inventoryItemDetails.getQuantity());
            InventoryItem updatedInventoryItem = inventoryItemRepository.save(item);
            return inventoryItemMapper.mapEntityToInventoryItemResponseDto(updatedInventoryItem);
        }
        return null;
    }

    //    Delete inventory by id
    @Transactional
    public void deleteInventory(Long id) {
        inventoryItemRepository.deleteById(id);
    }
}
