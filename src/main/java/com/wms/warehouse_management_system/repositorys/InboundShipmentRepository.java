package com.wms.warehouse_management_system.repositorys;

import com.wms.warehouse_management_system.entities.InboundShipment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InboundShipmentRepository extends JpaRepository<InboundShipment, Long> {

    @EntityGraph(attributePaths = {"inboundShipmentItems"})
    List<InboundShipment> findAll();

    @EntityGraph(attributePaths = {"inboundShipmentItems"})
    Optional<InboundShipment> findById(Long id);

}
