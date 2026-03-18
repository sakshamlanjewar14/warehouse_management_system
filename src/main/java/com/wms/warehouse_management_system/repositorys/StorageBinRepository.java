package com.wms.warehouse_management_system.repositorys;

import com.wms.warehouse_management_system.entities.StorageBin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StorageBinRepository extends JpaRepository<StorageBin, Long> {

    // HQL uses the Entity name (StorageBin) and its property (warehouseId)
    @Query("SELECT sb FROM StorageBin sb WHERE sb.warehouse.warehouseId = :warehouseId")
    List<StorageBin> getAllBinsByWarehouseId(Long warehouseId);
}
