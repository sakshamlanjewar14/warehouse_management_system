package com.wms.warehouse_management_system.services;


import com.wms.warehouse_management_system.entities.Warehouse;
import com.wms.warehouse_management_system.repositorys.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

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

    public void deleteWarehouse(Long id){
        warehouseRepository.deleteById(id);
    }

}
