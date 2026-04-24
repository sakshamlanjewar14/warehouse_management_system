package com.wms.warehouse_management_system.dtos;

import com.wms.warehouse_management_system.enums.InboundShipmentStatus;
import com.wms.warehouse_management_system.enums.OutboundShipmentStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OutboundShipmentResponseDto {
    private Long shipmentId;

    private String shipmentNumber;

    private String customerName;

    private OutboundShipmentStatus status;

    private String shipmentAddress;

    private  String trackingNumber;

    List<OutboundShipmentItemResponseDto> outboundShipmentItems = new ArrayList<>();
}
