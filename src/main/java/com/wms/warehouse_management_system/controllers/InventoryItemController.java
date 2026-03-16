package com.wms.warehouse_management_system.controllers;

import com.wms.warehouse_management_system.common.ApiResponse;
import com.wms.warehouse_management_system.entities.InventoryItem;
import com.wms.warehouse_management_system.services.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventoryitems")
public class InventoryItemController {

    private final  InventoryService inventoryService;

    public InventoryItemController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<InventoryItem>> createInventoryItem(@RequestBody InventoryItem inventoryItem){
        try{
            InventoryItem savedinventory = inventoryService.createInventory(inventoryItem);
            return ResponseEntity.ok(ApiResponse.success(savedinventory));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<InventoryItem>>> getAllItem(){
        List<InventoryItem> allInventory = inventoryService.getAllInventory();
        if (allInventory.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Inventoryitem not found"));
        }
        return ResponseEntity.ok(ApiResponse.success(allInventory));
    }
    
    @GetMapping("/{inventoryItemId}")
    public ResponseEntity<ApiResponse<InventoryItem>> getInventoryItemById(@PathVariable("inventoryItemId") Long inventoryItemId){
        InventoryItem inventoryById = inventoryService.getInventoryById(inventoryItemId);
        if(inventoryById == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Product not find for id :"+inventoryItemId));
        }
        return ResponseEntity.ok(ApiResponse.success(inventoryById));
    }

    @PutMapping("/{inventoryItemId}")
    public ResponseEntity<ApiResponse<InventoryItem>> updateInventoryItem(@PathVariable() Long inventoryItemId, @RequestBody InventoryItem inventoryItem){
        try{
            InventoryItem updatedInventoryItem = inventoryService.updateInventory(inventoryItemId, inventoryItem);
            if (updatedInventoryItem == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Inventoryitem Not Found"));
            }
            return ResponseEntity.ok(ApiResponse.success(updatedInventoryItem));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{inventoryItemId}")
    public ResponseEntity<ApiResponse<String>> deleteInventoryItem(@PathVariable("inventoryItemId") Long inventoryItemId){
        try{
            inventoryService.deleteInventory(inventoryItemId);
            return ResponseEntity.ok(ApiResponse.success("Inventoryitem with id :"+inventoryItemId+ "deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(e.getMessage()));
        }
    }
}
