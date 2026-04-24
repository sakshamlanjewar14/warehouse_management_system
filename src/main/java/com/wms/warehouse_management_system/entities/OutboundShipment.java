package com.wms.warehouse_management_system.entities;

import com.wms.warehouse_management_system.enums.OutboundShipmentStatus;
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

    @Column(nullable = false)
    private String customerName;

    @Enumerated(EnumType.STRING)
    private OutboundShipmentStatus status;

    @Column(nullable = false)
    private String shipmentAddress;

    @Column(nullable = false)
    private  String trackingNumber;

    // 🔗 One shipment has many items
    @OneToMany(mappedBy= "outboundShipment", cascade = CascadeType.ALL,  orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OutboundShipmentItem> outboundShipmentItems;

}
