package com.wms.warehouse_management_system.controllers;

import com.wms.warehouse_management_system.common.ApiResponse;
import com.wms.warehouse_management_system.dtos.StockTransferRequestDto;
import com.wms.warehouse_management_system.dtos.StockTransferResponseDto;
import com.wms.warehouse_management_system.services.StockTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stock_transfer")
public class StockTransferController {

    private final StockTransferService stockTransferService;

    @PostMapping
    public ResponseEntity<ApiResponse<StockTransferResponseDto>> createStockTransfer(@RequestBody StockTransferRequestDto stockTransfer){
        try{
            StockTransferResponseDto savedStockTransfrerItem = stockTransferService.createStockTransfer(stockTransfer);
            return ResponseEntity.ok(ApiResponse.success(savedStockTransfrerItem));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StockTransferResponseDto>>> getAllStockTransfer(){
        List<StockTransferResponseDto> stockTransferResponseDtos = stockTransferService.getAllStockTransfer();
        if (stockTransferResponseDtos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("StockTransfer not found"));
        }
        return ResponseEntity.ok(ApiResponse.success(stockTransferResponseDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StockTransferResponseDto>> getStockTransferById(@PathVariable("id") Long id) {
        StockTransferResponseDto stockTransferResponseDto = stockTransferService.getStockTransferById(id);
        if (stockTransferResponseDto == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Stock transfer not found for Id"+ id));
        }
        return ResponseEntity.ok(ApiResponse.success(stockTransferResponseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteStockTransfer(@PathVariable("id") Long id){
        try {
            stockTransferService.deleteStockTransfer(id);
            return ResponseEntity.ok(ApiResponse.success("Stock transfer with id:"+ id + "deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StockTransferResponseDto>> updateStockTransfer(@PathVariable("id") long id,
                                                                                     @RequestBody StockTransferRequestDto requestDto){
        try {
            StockTransferResponseDto updateStockTransfer = stockTransferService.updateStockTransfer(id, requestDto);
            if (updateStockTransfer == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Stock transfer not found"));
            }
            return ResponseEntity.ok(ApiResponse.success(updateStockTransfer));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}
