package com.wms.warehouse_management_system.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "stock_trasfer_items")
public class StockTransferItem extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id")
    private StockTransfer stockTransfer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;
}
