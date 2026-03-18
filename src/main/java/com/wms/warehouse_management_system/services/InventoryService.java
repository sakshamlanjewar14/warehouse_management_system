package com.wms.warehouse_management_system.services;

import com.wms.warehouse_management_system.entities.InventoryItem;
import com.wms.warehouse_management_system.repositorys.InventoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class InventoryService {

    @Autowired
    public  InventoryItemRepository inventoryItemRepository;

//    Create inventory
    public InventoryItem createInventory(InventoryItem inventory){
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
    public void deleteInventory(Long id){
        inventoryItemRepository.deleteById(id);
    }
}
