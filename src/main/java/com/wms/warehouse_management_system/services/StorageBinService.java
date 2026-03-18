package com.wms.warehouse_management_system.services;

import com.wms.warehouse_management_system.dtos.StorageBinResponseDto;
import com.wms.warehouse_management_system.entities.StorageBin;
import com.wms.warehouse_management_system.repositorys.StorageBinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StorageBinService {

    private final StorageBinRepository storageBinRepository;
    private final WarehouseService warehouseService;


//    Create storagebin
    public StorageBinResponseDto createBin(Long warehouseId, StorageBin bin){
        StorageBin savedStorageBin = storageBinRepository.save(bin);
        List<StorageBin> storageBinList = this.getAllBinsByWarehouseId(warehouseId);
        warehouseService.updateWarehouseCapacityByWarehouseId(storageBinList, warehouseId);
        return savedStorageBin.toResponseDto();
    }

//    Get All bins
    public List<StorageBinResponseDto> getAllBins(){
        return storageBinRepository.findAll().stream().map(StorageBin::toResponseDto).toList();
    }

//    Get bins by id
    public StorageBinResponseDto getBinById(Long storageBinId){
        Optional<StorageBin> storageBinOptional = storageBinRepository.findById(storageBinId);
        return storageBinOptional.map(StorageBin::toResponseDto).orElse(null);
    }

//    Update bin by id
    public StorageBinResponseDto updateBin(Long warehouseId, Long storageBinId, StorageBin storageBinDetails){
        if(!Objects.equals(storageBinId, storageBinDetails.getBinId())){
            return null;
        }
        StorageBin storageBin = storageBinRepository.findById(storageBinId).orElse(null);

        if(storageBin != null){
            storageBin.setBinCode(storageBinDetails.getBinCode());
            storageBin.setCapacity(storageBinDetails.getCapacity());
            StorageBin updatedStorageBin = storageBinRepository.save(storageBin);
            List<StorageBin> storageBinList = this.getAllBinsByWarehouseId(warehouseId);
            warehouseService.updateWarehouseCapacityByWarehouseId(storageBinList, warehouseId);
            return updatedStorageBin.toResponseDto();
        }
        return  null;
    }

//    Delete bin by id
    public void deleteBin(Long warehouseId, Long storageBinId){
        storageBinRepository.deleteById(storageBinId);
        List<StorageBin> storageBinList = this.getAllBinsByWarehouseId(warehouseId);
        warehouseService.updateWarehouseCapacityByWarehouseId(storageBinList, warehouseId);
    }

    private List<StorageBin> getAllBinsByWarehouseId(Long warehouseId) {
        return storageBinRepository.getAllBinsByWarehouseId(warehouseId);
    }
}
