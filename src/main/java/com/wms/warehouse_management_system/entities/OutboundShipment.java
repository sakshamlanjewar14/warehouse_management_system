package com.wms.warehouse_management_system.entities;

import com.wms.warehouse_management_system.enums.InboundShipmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "outbound_shipments")
public class OutboundShipment extends  BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipmentId;

    @Column(unique = true, nullable = false)
    private String shipmentNumber;

    @Enumerated(EnumType.STRING)
    private InboundShipmentStatus status;

    private String shipmentAddress;

    private  String trackingNumber;

    // 🔗 One shipment has many items
    @OneToMany(mappedBy= "outboundShipment", cascade = CascadeType.ALL,  orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OutboundShipmentItem> outboundShipmentItems;

}
