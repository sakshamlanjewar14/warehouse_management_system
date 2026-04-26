package com.wms.warehouse_management_system.repositorys;

import com.wms.warehouse_management_system.entities.StockTransferItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StokTransferItemRepository extends JpaRepository<StockTransferItem, Long> {
}
