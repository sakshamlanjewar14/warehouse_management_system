package com.wms.warehouse_management_system.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "product")
//@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product extends BaseEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String sku;

    @Column
    private String description;

    @Column(unique = true)
    private String barcode;

    @Column
    private BigDecimal price;

    @Column
    private Double weight;

    @Column
    private String imageUrl;

    @OneToMany(mappedBy = "product")  //One Product → Many InventoryItems
    private List<InventoryItem> inventoryItems;
}
