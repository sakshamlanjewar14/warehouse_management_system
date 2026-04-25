package com.wms.warehouse_management_system.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "outbound_shipment_item")
public class OutboundShipmentItem extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipmentItemId;

    @Column(nullable = false)
    private Integer quantity;

//  Many items belong to one shipment
    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private OutboundShipment outboundShipment;

    //Many items refer to one product
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
