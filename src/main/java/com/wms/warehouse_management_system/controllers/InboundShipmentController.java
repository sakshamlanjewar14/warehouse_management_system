package com.wms.warehouse_management_system.controllers;

import com.wms.warehouse_management_system.common.ApiResponse;
import com.wms.warehouse_management_system.dtos.InboundShipmentItemResponseDto;
import com.wms.warehouse_management_system.dtos.InboundShipmentRequestDto;
import com.wms.warehouse_management_system.dtos.InboundShipmentResponseDto;
import com.wms.warehouse_management_system.dtos.SupplierResponseDto;
import com.wms.warehouse_management_system.entities.InboundShipmentItem;
import com.wms.warehouse_management_system.services.InboundShipmentService;
import com.wms.warehouse_management_system.services.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/inbound_shipment")
public class InboundShipmentController {
    @Autowired
    private final InboundShipmentService inboundShipmentService;
    private final SupplierService supplierService;

    @PostMapping
    public ResponseEntity<ApiResponse<InboundShipmentResponseDto>> createInboundShipment(
            @RequestBody InboundShipmentRequestDto responseDto){
        try{
            InboundShipmentResponseDto savedInboundShipmentItem = inboundShipmentService.createShipment(responseDto);
            return ResponseEntity.ok(ApiResponse.success(savedInboundShipmentItem));
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InboundShipmentResponseDto>>> getAllInboundShipments(){
        List<InboundShipmentResponseDto> inboundShipmentResponseDtos = inboundShipmentService.getAllShipment();
        if (inboundShipmentResponseDtos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Inbound Shipments Not Found"));
        }
        return ResponseEntity.ok(ApiResponse.success(inboundShipmentResponseDtos));
    }

    @GetMapping("/{shipmentId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getInboundShipmentById(
            @PathVariable("shipmentId") Long shipmentId,
            @PathVariable("supplierId") Long supplierId){
        Map<String, Object> responseMap = new HashMap<>();

//        For the inboundshipment
        InboundShipmentResponseDto inboundShipmentResponseDto = inboundShipmentService.getShipmentById(shipmentId);
        if (inboundShipmentResponseDto == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Inbound Shipment Not Found For Id :"+shipmentId));
        }

//        For the supplier
        SupplierResponseDto supplierResponseDto = supplierService.getSupplierById(supplierId);
        if (supplierResponseDto == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Supplier  Not Found For Id :"+supplierId));
        }

        responseMap.put("inboundShipmentResponseDto", inboundShipmentResponseDto);
        responseMap.put("supplierResponseDto", supplierResponseDto);

        return ResponseEntity.ok(ApiResponse.success(responseMap));

    }

    @DeleteMapping("/{shipmentId}")
    public ResponseEntity<ApiResponse<String>> deleteInboundShipment(@PathVariable("shipmentId") Long shipmentId){
        try{
            inboundShipmentService.deleteShipment(shipmentId);
            return ResponseEntity.ok(ApiResponse.success("Inbound Shipment with Id :"+shipmentId+" deleted successfully"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/{shipmentId}")
    public ResponseEntity<ApiResponse<InboundShipmentResponseDto>> updateInboundShipment(@PathVariable("shipmentId") Long shipmentId,
                                                                                  @RequestBody InboundShipmentRequestDto requestDto){
        try{
            InboundShipmentResponseDto updatedInboundShipmentResponseDto = inboundShipmentService.updateShipment(shipmentId, requestDto);
            if (updatedInboundShipmentResponseDto == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Inbound Shipment not found"));
            }
            return ResponseEntity.ok(ApiResponse.success(updatedInboundShipmentResponseDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }


}
