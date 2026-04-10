package com.wms.warehouse_management_system.repositorys;

import com.wms.warehouse_management_system.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SupplierRepository extends JpaRepository<Supplier,Long> {
}
