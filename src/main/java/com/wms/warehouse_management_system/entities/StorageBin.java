package com.wms.warehouse_management_system.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wms.warehouse_management_system.dtos.StorageBinResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "storage_bin")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StorageBin extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long binId;

    @Column(unique = true)
    private String binCode;

    @Column
    private Integer capacity;

    @ManyToOne //Many StorageBins → One Warehouse
    @JoinColumn(name = "warehouse_id")
    @JsonIgnore
    private Warehouse warehouse;

    @OneToMany(mappedBy = "storageBin") //One StorageBin → Many InventoryItems
    private List<InventoryItem> inventoryItems;

    public StorageBinResponseDto toResponseDto() {
        StorageBinResponseDto storageBinResponseDto = new StorageBinResponseDto();
        storageBinResponseDto.setBinId(this.getBinId());
        storageBinResponseDto.setBinCode(this.getBinCode());
        storageBinResponseDto.setCapacity(this.getCapacity());
        storageBinResponseDto.setWarehouseName(this.getWarehouse().getName());
        storageBinResponseDto.setInventoryItems(this.getInventoryItems());
        return storageBinResponseDto;
    }
}
