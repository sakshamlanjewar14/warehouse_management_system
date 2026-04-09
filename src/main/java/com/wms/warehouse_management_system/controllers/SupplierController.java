package com.wms.warehouse_management_system.controllers;


import com.wms.warehouse_management_system.common.ApiResponse;
import com.wms.warehouse_management_system.dtos.SupplierResponseDto;
import com.wms.warehouse_management_system.entities.Supplier;
import com.wms.warehouse_management_system.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {

    @Autowired
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SupplierResponseDto>> createSupplier(@RequestBody Supplier supplier){
       try{
           Supplier savedSupplier = supplierService.createSupplier(supplier);
           return ResponseEntity.ok(ApiResponse.success(savedSupplier));
       } catch (Exception e) {

           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(ApiResponse.error(e.getMessage()));
       }

    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Supplier>>> getAllSupplier(){
        List<Supplier> suppliers = supplierService.getAllSupplier();
        if (suppliers.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Supplier Not Found"));
        }
        return ResponseEntity.ok(ApiResponse.success(suppliers));
    }

    @GetMapping("/{supplierId}")
    public ResponseEntity<ApiResponse<Supplier>> getSupplierById(@PathVariable("supplierId") Long supplierId){
        Supplier supplier = supplierService.getSupplierById(supplierId);
        if (supplier == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Supplier Not Found For Id :"+ supplierId));
        }
        return ResponseEntity.ok(ApiResponse.success(supplierId));
    }

    @DeleteMapping("/{supplierId}")
    public ResponseEntity<ApiResponse<String>> deleteSupplier(@PathVariable("supplierId") Long supplierId){
        try {
            supplierService.deleteSupplier(supplierId);
            return ResponseEntity.ok(ApiResponse.success("Supplier With Id :"+ supplierId +"Deleted Successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{supplierId}")
    public ResponseEntity<ApiResponse<Supplier>> updateSupplier(@PathVariable("supplierId") Long supplierId, @RequestBody Supplier supplier){
        try {
            Supplier updateSupplier = supplierService.updateSupplier(supplierId, supplier);
            if (updateSupplier ==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Supplier not Found"));
            }
            return ResponseEntity.ok(ApiResponse.success(updateSupplier));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}
