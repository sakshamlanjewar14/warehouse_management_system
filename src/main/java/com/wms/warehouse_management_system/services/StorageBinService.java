package com.wms.warehouse_management_system.services;

import com.wms.warehouse_management_system.entities.StorageBin;
import com.wms.warehouse_management_system.repositorys.StorageBinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageBinService {

    @Autowired
    private StorageBinRepository storageBinRepository;

//    Create storagebin
    public StorageBin createBin(StorageBin bin){
        return storageBinRepository.save(bin);
    }

//    Get All bins
    public List<StorageBin> getAllBins(){
        return storageBinRepository.findAll();
    }

//    Get bins by id
    public StorageBin getBinById(Long id){
        return storageBinRepository.findById(id).orElse(null);
    }

//    Delete bin by id
    public void deleteBin(Long id){
        storageBinRepository.deleteById(id);
    }

}
