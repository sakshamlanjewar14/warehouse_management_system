package com.wms.warehouse_management_system.controllers;

import com.wms.warehouse_management_system.common.ApiResponse;
import com.wms.warehouse_management_system.dtos.SupplierItemRequestDto;
import com.wms.warehouse_management_system.dtos.SupplierItemResponseDto;
import com.wms.warehouse_management_system.entities.SupplierItem;
import com.wms.warehouse_management_system.services.SupplierItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
public class SupplierItemController {

    private final SupplierItemService supplierItemService;

    public SupplierItemController(SupplierItemService supplierItemService) {
        this.supplierItemService = supplierItemService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SupplierItemResponseDto>> createItem(@RequestBody SupplierItemRequestDto item){
        try{
            SupplierItemResponseDto savedItem = supplierItemService.createItem(item);
            return ResponseEntity.ok(ApiResponse.success(savedItem));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SupplierItemResponseDto>>> getAllItem(){
        List<SupplierItemResponseDto> supplierItems = supplierItemService.getAllItem();
        if ((supplierItems.isEmpty())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Item Not Found"));
        }
        return  ResponseEntity.ok(ApiResponse.success(supplierItems));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ApiResponse<SupplierItemResponseDto>> getItemById(@PathVariable("itemId") Long itemId){
        SupplierItemResponseDto supplierItem = supplierItemService.getItemById(itemId);
        if (supplierItem == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Item Not Found For Id :"+ itemId));
        }
        return ResponseEntity.ok(ApiResponse.success(supplierItem));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<ApiResponse<String>> deleteItem(@PathVariable("itemId") Long itemId){
        try{
            supplierItemService.deleteItem(itemId);
            return ResponseEntity.ok(ApiResponse.success("Item With Id :"+ itemId +"Deleted Successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ApiResponse<SupplierItemResponseDto>> updateItem(@PathVariable("itemId") Long itemId, @RequestBody SupplierItemRequestDto supplierItem ){
        try {
            SupplierItemResponseDto updateItem = supplierItemService.updateItem(itemId, supplierItem);
            if (updateItem == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("item Not Found"));
            }
            return ResponseEntity.ok(ApiResponse.success(updateItem));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}
