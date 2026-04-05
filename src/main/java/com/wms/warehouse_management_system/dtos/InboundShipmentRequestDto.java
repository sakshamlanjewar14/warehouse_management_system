package com.wms.warehouse_management_system.dtos;

import com.wms.warehouse_management_system.entities.InboundShipment;
import com.wms.warehouse_management_system.entities.InboundShipmentItem;
import com.wms.warehouse_management_system.enums.ShipmentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class InboundShipmentRequestDto {

    private Long shipmentId;

    private String shipmentCode;

    private  String supplierName;

    private String status;

    private LocalDateTime expectedDate;

    private LocalDateTime receivedDate;

    private String referenceNumber;

    private String notes;

    List<InboundShipmentItemRequestDto> inboundShipmentItems = new ArrayList<>();

}
