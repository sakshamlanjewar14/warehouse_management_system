package com.wms.warehouse_management_system.controllers;

import com.wms.warehouse_management_system.common.ApiResponse;
import com.wms.warehouse_management_system.dtos.OutboundShipmentRequestDto;
import com.wms.warehouse_management_system.dtos.OutboundShipmentResponseDto;
import com.wms.warehouse_management_system.services.OutboundShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/outbound_shipment")
public class OutboundShipmentController {

    @Autowired
    private final OutboundShipmentService outboundShipmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<OutboundShipmentResponseDto>> createShipment(@RequestBody OutboundShipmentRequestDto outboundShipment) {
        try {
            OutboundShipmentResponseDto savedOutboundShipmentItem = outboundShipmentService.createShipment(outboundShipment);
            return ResponseEntity.ok(ApiResponse.success(savedOutboundShipmentItem));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<OutboundShipmentResponseDto>>> getAllShipment() {
        List<OutboundShipmentResponseDto> outboundShipmentResponseDtos = outboundShipmentService.getAllShipment();
        if (outboundShipmentResponseDtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Shipment not found"));
        }
        return ResponseEntity.ok(ApiResponse.success(outboundShipmentResponseDtos));
    }

    @GetMapping("/{shipmentId}")
    public ResponseEntity<ApiResponse<OutboundShipmentResponseDto>> getShipmentById(@PathVariable("shipmentId") Long shipmentid) {
        OutboundShipmentResponseDto outboundShipmentResponseDto = outboundShipmentService.getShipmentByid(shipmentid);
        if (outboundShipmentResponseDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("shipment not found for Id :" + shipmentid));
        }
        return ResponseEntity.ok(ApiResponse.success(outboundShipmentResponseDto));
    }


    @DeleteMapping("/{shipmentId}")
    public ResponseEntity<ApiResponse<String>> deleteShipment(@PathVariable("shipmentId") Long shipmentId) {
        try {
            outboundShipmentService.deleteShipment(shipmentId);
            return ResponseEntity.ok(ApiResponse.success("Shipment with Id:" + shipmentId + "Deleted Successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }


    @PutMapping("/{shipmentId}")
    public ResponseEntity<ApiResponse<OutboundShipmentResponseDto>> updateShipment(@PathVariable("shipmentId") Long shipmentId,
                                                                                   @RequestBody OutboundShipmentRequestDto requestDto){
        try {
            OutboundShipmentResponseDto updateShipment = outboundShipmentService.updateShipment(shipmentId,requestDto);
            if (updateShipment == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Shipment Not Found"));
            }
            return ResponseEntity.ok(ApiResponse.success(updateShipment));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}

