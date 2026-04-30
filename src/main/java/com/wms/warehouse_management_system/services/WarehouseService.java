package com.wms.warehouse_management_system.services;


import com.wms.warehouse_management_system.dtos.WarehouseResponseDto;
import com.wms.warehouse_management_system.entities.StorageBin;
import com.wms.warehouse_management_system.entities.Warehouse;
import com.wms.warehouse_management_system.mapper.WarehouseMapper;
import com.wms.warehouse_management_system.repositorys.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    @Transactional
    //    Craete warehouse
    public WarehouseResponseDto createWarehouse(Warehouse warehouse) {
        Warehouse savedWarehouse = warehouseRepository.save(warehouse);
        return warehouseMapper.mapEntityToWarehouseResponseDto(savedWarehouse);
    }

    @Transactional(readOnly = true)
    //    Get all warehouses
    public List<WarehouseResponseDto> getAllWarehouses() {
        return warehouseRepository.findAll().stream().map(warehouseMapper::mapEntityToWarehouseResponseDto).toList();
    }

    @Transactional(readOnly = true)
    //    Get warehouse by id
    public WarehouseResponseDto getWarehouseById(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id).orElse(null);
        if (warehouse != null) {
            return warehouseMapper.mapEntityToWarehouseResponseDto(warehouse); //if id will not be there it will return null
        }
        return null;
    }

    @Transactional
    public Warehouse getWarehouseEntityById(Long id) {
        return warehouseRepository.findById(id).orElse(null); //if id will not be there it will return null
    }

    @Transactional
    //    Delete Warehouse
    public void deleteWarehouse(Long id) {
        warehouseRepository.deleteById(id);
    }

    @Transactional
    public WarehouseResponseDto updateWarehouse(Long warehouseId, Warehouse warehouseDetails) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId).orElse(null);
        if (warehouse != null) {
            warehouse.setName(warehouseDetails.getName());
            warehouse.setLocation(warehouseDetails.getLocation());
            warehouse.setCapacity(warehouseDetails.getCapacity());
            Warehouse savedWarehouse = warehouseRepository.save(warehouse);
            return warehouseMapper.mapEntityToWarehouseResponseDto(savedWarehouse);
        }
        return null;
    }

    @Transactional
    public void updateWarehouseCapacityByWarehouseId(List<StorageBin> storageBinList, Long warehouseId) {
        Integer sumOfAllStorageBinsCapacity = storageBinList.stream().mapToInt(StorageBin::getCapacity).sum();
        warehouseRepository.updateWarehouseCapacityByWarehouseId(sumOfAllStorageBinsCapacity, warehouseId);
    }
}
