package com.wms.warehouse_management_system.dtos;

import lombok.Data;

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
