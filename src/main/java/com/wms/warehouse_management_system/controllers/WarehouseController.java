package com.wms.warehouse_management_system.controllers;

import com.wms.warehouse_management_system.common.ApiResponse;
import com.wms.warehouse_management_system.entities.Warehouse;
import com.wms.warehouse_management_system.services.WarehouseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService){
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Warehouse>> createWarehouse(@RequestBody Warehouse warehouse){
        try {
            Warehouse savedWarShouse = warehouseService.createWarehouse(warehouse);
            return ResponseEntity.ok(ApiResponse.success(savedWarShouse));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(e.getMessage()));
        }

    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Warehouse>>> getAllWarehouse(){
        List<Warehouse> warehouseList = warehouseService.getAllWarehouses();
        if(warehouseList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Warehouse not found"));
        }
        return ResponseEntity.ok(ApiResponse.success(warehouseList));
    }

    @GetMapping("/{warehouseId}")
    public ResponseEntity<ApiResponse<Warehouse>> getWarehouseById(@PathVariable("warehouseId") Long warehouseId){
        Warehouse warehouse = warehouseService.getWarehouseById(warehouseId);
        if(warehouse == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Warehouse of " + warehouseId + "is not found"));
        }
        return ResponseEntity.ok(ApiResponse.success(warehouse));

    }

    @DeleteMapping("/{warehouseId}")
    public ResponseEntity<ApiResponse<String>> deleteWarehouseById(@PathVariable("warehouseId") Long warehouseId){
        try{
            warehouseService.deleteWarehouse(warehouseId);
            return ResponseEntity.ok(ApiResponse.success("Warehouse With Id :" + warehouseId +"Deleted Successfully"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(e.getMessage()));
        }

    }

    @PutMapping("/{warehouseId}")
    public ResponseEntity<ApiResponse<Warehouse>> updateWarehouse(@PathVariable("warehouseId") Long warehouseId, @RequestBody Warehouse warehouse){
        try {
            Warehouse updatedWarehouse = warehouseService.updateWarehouse(warehouseId, warehouse);
            if(updatedWarehouse == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Warehouse not found"));
            }
            return ResponseEntity.ok(ApiResponse.success(updatedWarehouse));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(e.getMessage()));
        }

    }
}
