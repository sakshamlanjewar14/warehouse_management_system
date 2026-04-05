package com.wms.warehouse_management_system.entities;

import com.wms.warehouse_management_system.dtos.InboundShipmentItemResponseDto;
import com.wms.warehouse_management_system.dtos.InboundShipmentResponseDto;
import com.wms.warehouse_management_system.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inbound_shipment")
public class InboundShipment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipmentId;

    @Column(nullable = false, unique = true)
    private String shipmentCode;

    @Column(nullable = false)
    private  String supplierName;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    @Column
    private LocalDateTime expectedDate;

    @Column
    private LocalDateTime receivedDate;

    @Column
    private String referenceNumber;

    @Column
    private String notes;

    @OneToMany(mappedBy = "inboundShipment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<InboundShipmentItem> inboundShipmentItems = new ArrayList<>();

}
