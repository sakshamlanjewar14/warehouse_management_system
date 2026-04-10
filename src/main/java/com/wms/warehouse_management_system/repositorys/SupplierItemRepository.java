package com.wms.warehouse_management_system.repositorys;

import com.wms.warehouse_management_system.entities.SupplierItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierItemRepository extends JpaRepository<SupplierItem, Long> {
}
