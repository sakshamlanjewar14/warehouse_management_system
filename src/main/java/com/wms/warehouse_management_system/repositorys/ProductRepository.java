package com.wms.warehouse_management_system.repositorys;

import com.wms.warehouse_management_system.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
