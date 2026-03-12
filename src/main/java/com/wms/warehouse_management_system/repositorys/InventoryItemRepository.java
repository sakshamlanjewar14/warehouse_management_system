package com.wms.warehouse_management_system.repositorys;

import com.wms.warehouse_management_system.entities.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
}
