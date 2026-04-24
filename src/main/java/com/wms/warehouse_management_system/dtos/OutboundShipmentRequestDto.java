package com.wms.warehouse_management_system.dtos;

import com.wms.warehouse_management_system.enums.OutboundShipmentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OutboundShipmentRequestDto {
    private Long shipmentId;

    private String shipmentNumber;

    private String customerName;

    private OutboundShipmentStatus status;

    private String shipmentAddress;

    private  String trackingNumber;

    List<OutboundShipmentItemRequestDto> outboundShipmentItems = new ArrayList<>();
}
