package com.wms.warehouse_management_system.services;

import com.wms.warehouse_management_system.entities.StorageBin;
import com.wms.warehouse_management_system.repositorys.StorageBinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
    public StorageBin getBinById(Long storageBinId){
        return storageBinRepository.findById(storageBinId).orElse(null);
    }

//    Update bin by id
    public StorageBin updateBin(Long storageBinId, StorageBin storageBinDetails){
        if(!Objects.equals(storageBinId, storageBinDetails.getBinId())){
            return null;
        }
        StorageBin storageBin = storageBinRepository.findById(storageBinId).orElse(null);

        if(storageBin != null){
            storageBin.setBinCode(storageBinDetails.getBinCode());
            storageBin.setCapacity(storageBinDetails.getCapacity());
            return storageBinRepository.save(storageBin);
        }
        return  null;
    }

//    Delete bin by id
    public void deleteBin(Long storageBinId){
        storageBinRepository.deleteById(storageBinId);
    }

}
