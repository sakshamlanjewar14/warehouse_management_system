package com.wms.warehouse_management_system.repositorys;

import com.wms.warehouse_management_system.entities.StorageBin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageBinRepository extends JpaRepository<StorageBin, Long> {
}
