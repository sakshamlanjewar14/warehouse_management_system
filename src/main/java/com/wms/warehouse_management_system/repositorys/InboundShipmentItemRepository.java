package com.wms.warehouse_management_system.repositorys;

import com.wms.warehouse_management_system.entities.InboundShipmentItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InboundShipmentItemRepository extends JpaRepository<InboundShipmentItem, Long> {
}
