package com.wms.warehouse_management_system.dtos;

import com.wms.warehouse_management_system.entities.OutboundShipment;
import com.wms.warehouse_management_system.entities.Product;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class OutboundShipmentItemRequestDto {
    private Long shipmentItemId;

    private Integer quantity;

    private Long shipmentId;

    private Long productId;

    private String productName;

}
