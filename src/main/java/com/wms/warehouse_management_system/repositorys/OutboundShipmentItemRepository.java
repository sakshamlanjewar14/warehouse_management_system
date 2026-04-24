package com.wms.warehouse_management_system.repositorys;

import com.wms.warehouse_management_system.entities.OutboundShipmentItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboundShipmentItemRepository extends JpaRepository<OutboundShipmentItem, Long> {
}
