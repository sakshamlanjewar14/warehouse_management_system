package com.wms.warehouse_management_system.services;


import com.wms.warehouse_management_system.entities.StorageBin;
import com.wms.warehouse_management_system.entities.Warehouse;
import com.wms.warehouse_management_system.repositorys.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

//    Craete warehouse
    public Warehouse createWarehouse(Warehouse warehouse){
        return warehouseRepository.save(warehouse);
    }

//    Get all warehouses
    public List<Warehouse> getAllWarehouses(){
        return warehouseRepository.findAll();
    }

//    Get warehouse by id
    public Warehouse getWarehouseById(Long id){
        return warehouseRepository.findById(id).orElse(null); //if id will not be there it will return null
    }

//    Delete Warehouse
    public void deleteWarehouse(Long id){
        warehouseRepository.deleteById(id);
    }

    public Warehouse updateWarehouse(Long warehouseId, Warehouse warehouseDetails) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId).orElse(null);
        if (warehouse != null){
            warehouse.setName(warehouseDetails.getName());
            warehouse.setLocation(warehouseDetails.getLocation());
            warehouse.setCapacity(warehouseDetails.getCapacity());

            return warehouseRepository.save(warehouse);
        }
        return null;
    }

    public void updateWarehouseCapacityByWarehouseId(List<StorageBin> storageBinList, Long warehouseId) {
        Integer sumOfAllStorageBinsCapacity = storageBinList.stream().mapToInt(StorageBin::getCapacity).sum();
        warehouseRepository.updateWarehouseCapacityByWarehouseId(sumOfAllStorageBinsCapacity, warehouseId);
    }
}
