package com.wms.warehouse_management_system.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wms.warehouse_management_system.dtos.InventoryItemResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItem extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryItemId;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne //Many InventoryItems → One Product
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @ManyToOne  //Many InventoryItems → One StorageBin
    @JoinColumn(name = "bin_id")
    @JsonIgnore
    private StorageBin storageBin;

}
