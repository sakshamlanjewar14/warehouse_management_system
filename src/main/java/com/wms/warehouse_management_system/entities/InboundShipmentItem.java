package com.wms.warehouse_management_system.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wms.warehouse_management_system.dtos.InboundShipmentItemResponseDto;
import com.wms.warehouse_management_system.enums.ShipmentItemStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "inbound_shipment_item")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InboundShipmentItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipmentItemId;

    @Column(nullable = false)
    public Long productId;

    @Column
    public Integer expectedQty;

    @Column
    public Integer receivedQty;

    @Column
    public Integer damagedQty;

    @Enumerated(EnumType.STRING)
    private ShipmentItemStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id", nullable = false)
    @JsonIgnore
    private InboundShipment inboundShipment;

}
