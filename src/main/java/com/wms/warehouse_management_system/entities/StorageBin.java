package com.wms.warehouse_management_system.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "storage_bin")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StorageBin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long binId;

    @Column(unique = true)
    private String binCode;

    @Column
    private Integer capacity;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne //Many StorageBins → One Warehouse
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @OneToMany(mappedBy = "storageBin") //One StorageBin → Many InventoryItems
    private List<InventoryItem> inventoryItems;
}
