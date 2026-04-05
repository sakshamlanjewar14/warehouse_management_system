package com.wms.warehouse_management_system.dtos;

import com.wms.warehouse_management_system.entities.InboundShipmentItem;
import com.wms.warehouse_management_system.enums.ShipmentItemStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class InboundShipmentResponseDto {

    private Long shipmentId;

    private String shipmentCode;

    private  String supplierName;

    private String status;

    private LocalDateTime expectedDate;

    private LocalDateTime receivedDate;

    private String referenceNumber;

    private String notes;

    List<InboundShipmentItemResponseDto> inboundShipmentItems = new ArrayList<>();

}
