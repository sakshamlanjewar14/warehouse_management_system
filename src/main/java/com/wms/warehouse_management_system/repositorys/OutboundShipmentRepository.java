package com.wms.warehouse_management_system.repositorys;

import com.wms.warehouse_management_system.entities.OutboundShipment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OutboundShipmentRepository extends JpaRepository<OutboundShipment, Long> {
}
