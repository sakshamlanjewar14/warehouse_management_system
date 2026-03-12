package com.wms.warehouse_management_system.services;

import com.wms.warehouse_management_system.entities.InventoryItem;
import com.wms.warehouse_management_system.repositorys.InventoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    public InventoryItemRepository inventoryItemRepository;

//    Create inventory
    public InventoryItem addInventory(InventoryItem inventory){
        return inventoryItemRepository.save(inventory);
    }

//    Get all inventory
    public List<InventoryItem> getAllInventory(){
        return inventoryItemRepository.findAll();
    }

//    Get inventory by id
    public InventoryItem getInventoryById(Long id){
        return inventoryItemRepository.findById(id).orElse(null);
    }

//    Update quantity
    public InventoryItem updateQuantity(Long id, Integer quantity){
        InventoryItem item = inventoryItemRepository.findById(id).orElse(null);
        if(item != null){
            item.setQuantity(quantity);
            return inventoryItemRepository.save(item);
        }
        return null;
    }

//    Delete by id
    public void deleteInventory(Long id){
        inventoryItemRepository.deleteById(id);
    }
}
