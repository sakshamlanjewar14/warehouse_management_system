package com.wms.warehouse_management_system.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wms.warehouse_management_system.entities.InboundShipment;
import com.wms.warehouse_management_system.entities.InboundShipmentItem;
import com.wms.warehouse_management_system.enums.ShipmentItemStatus;
import com.wms.warehouse_management_system.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InboundShipmentItemRequestDto {

    private Long shipmentItemId;

    public Long productId;

    public Integer expectedQty;

    public Integer receivedQty;

    public Integer damagedQty;

    private String status;

    private Long inboundShipmentId;

}
