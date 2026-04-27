package com.wms.warehouse_management_system.controllers;


import com.wms.warehouse_management_system.common.ApiResponse;
import com.wms.warehouse_management_system.dtos.StorageBinRequestDto;
import com.wms.warehouse_management_system.dtos.StorageBinResponseDto;
import com.wms.warehouse_management_system.entities.StorageBin;
import com.wms.warehouse_management_system.services.StorageBinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/storagebins")
public class StorageBinController {

    private final StorageBinService storageBinService;

    public StorageBinController(StorageBinService storageBinService) {
        this.storageBinService = storageBinService;
    }

    @PostMapping("/{warehouseId}")
    public ResponseEntity<ApiResponse<StorageBinResponseDto>> createBin(
            @PathVariable ("warehouseId") Long warehouseId,
            @RequestBody StorageBinRequestDto storageBin){
        try {
            StorageBinResponseDto savedStorageBin = storageBinService.createBin(warehouseId, storageBin);
            return ResponseEntity.ok(ApiResponse.success(savedStorageBin));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StorageBinResponseDto>>> getAllBin(){
        List<StorageBinResponseDto> allBins = storageBinService.getAllBins();
        if(allBins.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("StorageBins Not Found"));
        }
        return ResponseEntity.ok(ApiResponse.success(allBins));
    }

    @GetMapping("/{storageBinId}")
    public ResponseEntity<ApiResponse<StorageBinResponseDto>> getBinById(@PathVariable ("storageBinId") Long storageBinId){
        StorageBinResponseDto binById = storageBinService.getBinById(storageBinId);
        if(binById == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Storage bin not found for this id"+storageBinId));
        }
        return ResponseEntity.ok(ApiResponse.success(binById));
    }

    @PutMapping("/{warehouseId}/{storageBinId}")
    public ResponseEntity<ApiResponse<StorageBinResponseDto>> updateBin(
            @PathVariable ("warehouseId") Long warehouseId,
            @PathVariable ("storageBinId") Long storageBinId,
            @RequestBody StorageBin storageBin){
        try {
            StorageBinResponseDto updatedStorageBin = storageBinService.updateBin(warehouseId, storageBinId, storageBin);
            if(updatedStorageBin == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Storage bin not found"));
            }
            return ResponseEntity.ok(ApiResponse.success(updatedStorageBin));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{warehouseId}/{storageBinId}")
    public ResponseEntity<ApiResponse<String>> deleteBin(
            @PathVariable ("warehouseId") Long warehouseId,
            @PathVariable("storageBinId") Long storageBinId){
        try {
            storageBinService.deleteBin(warehouseId, storageBinId);
            return ResponseEntity.ok(ApiResponse.success("Storagebin with id" +storageBinId+ "deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

}
