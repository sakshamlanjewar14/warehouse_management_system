package com.wms.warehouse_management_system.services;

import com.wms.warehouse_management_system.entities.SupplierItem;
import com.wms.warehouse_management_system.repositorys.SupplierItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SupplierItemService {

    @Autowired
    private SupplierItemRepository supplierItemRepository;

    public SupplierItem createItem(SupplierItem item){
        return supplierItemRepository.save(item);
    }

    public List<SupplierItem> getAllItem(){
        return supplierItemRepository.findAll();
    }

    public SupplierItem getItemById(itemId){
        return supplierItemRepository.findById(itemId).orElse(null);
    }

    public void deleteItem(Long itemId){
        supplierItemRepository.deleteById(itemId);
    }

    public SupplierItem updateItem(Long itemId, SupplierItem itemDetails){
        if(!Objects.equals(itemId, itemDetails.getItemId())){
            return null;
        }
        SupplierItem item = supplierItemRepository.findById(itemId).orElse(null);

        if(item != null){
            item.setProductName(itemDetails.getProductName());
            item.setPrice(itemDetails.getPrice());
            item.setQuantity(itemDetails.getQuantity());
            item.setProductId(itemDetails.getProductId());
            return supplierItemRepository.save(item);
        }
        return null;
    }



}
