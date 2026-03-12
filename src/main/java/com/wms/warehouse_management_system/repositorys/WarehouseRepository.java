package com.wms.warehouse_management_system.repositorys;

import com.wms.warehouse_management_system.entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
