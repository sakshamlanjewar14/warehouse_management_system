package com.wms.warehouse_management_system.repositorys;

import com.wms.warehouse_management_system.entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE Warehouse w SET w.capacity =:warehouseCapacity WHERE w.warehouseId = :warehouseId")
    void updateWarehouseCapacityByWarehouseId(Integer warehouseCapacity, Long warehouseId);
}
