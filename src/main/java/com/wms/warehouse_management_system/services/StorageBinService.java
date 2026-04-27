package com.wms.warehouse_management_system.services;

import com.wms.warehouse_management_system.dtos.StorageBinRequestDto;
import com.wms.warehouse_management_system.dtos.StorageBinResponseDto;
import com.wms.warehouse_management_system.entities.StorageBin;
import com.wms.warehouse_management_system.entities.Warehouse;
import com.wms.warehouse_management_system.mapper.StorageBinMapper;
import com.wms.warehouse_management_system.repositorys.StorageBinRepository;
import com.wms.warehouse_management_system.repositorys.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class StorageBinService {

    private final StorageBinRepository storageBinRepository;
    private final WarehouseService warehouseService;
    private final StorageBinMapper storageBinMapper;

    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random RANDOM = new Random();


//    Create storagebin
    public StorageBinResponseDto createBin(Long warehouseId, StorageBinRequestDto requestDto){
        requestDto.setBinCode(getUniqueBinCode());
        Warehouse warehouse = warehouseService.getWarehouseEntityById(warehouseId);
        if (warehouse != null){
            StorageBin storageBin = storageBinMapper.mapRequestDtoToStorageBinEntity(requestDto, warehouse);
            StorageBin savedStorageBin = storageBinRepository.save(storageBin);

            List<StorageBin> storageBinList = this.getAllBinsByWarehouseId(warehouseId);
            warehouseService.updateWarehouseCapacityByWarehouseId(storageBinList, warehouseId);
            return storageBinMapper.mapEntityToStorageBinResponseDto(savedStorageBin);
        }
        return null;
    }

//    Get All bins
    public List<StorageBinResponseDto> getAllBins(){
        return storageBinRepository.findAll().stream().map(storageBinMapper::mapEntityToStorageBinResponseDto).toList();
    }

//    Get bins by id
    public StorageBinResponseDto getBinById(Long storageBinId){
        Optional<StorageBin> storageBinOptional = storageBinRepository.findById(storageBinId);
        return storageBinOptional.map(storageBinMapper::mapEntityToStorageBinResponseDto).orElse(null);
    }

//    Update bin by id
    public StorageBinResponseDto updateBin(Long warehouseId, Long storageBinId, StorageBin storageBinDetails){
        if(!Objects.equals(storageBinId, storageBinDetails.getBinId())){
            return null;
        }
        StorageBin storageBin = storageBinRepository.findById(storageBinId).orElse(null);

        if(storageBin != null){
            storageBin.setBinCode(storageBin.getBinCode());
            storageBin.setCapacity(storageBinDetails.getCapacity());
            StorageBin updatedStorageBin = storageBinRepository.save(storageBin);
            List<StorageBin> storageBinList = this.getAllBinsByWarehouseId(warehouseId);
            warehouseService.updateWarehouseCapacityByWarehouseId(storageBinList, warehouseId);
            return storageBinMapper.mapEntityToStorageBinResponseDto(updatedStorageBin);
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

    public String getUniqueBinCode(){
        // 1. Get current time in milliseconds
        long timestamp = System.currentTimeMillis();

        // 2. Convert to Base36 (alphanumeric) to keep it short
        // Example: 1711550000000 -> "IX2Z6S00"
        String timePart = Long.toString(timestamp, 36).toUpperCase();

        // 3. Generate a 3-character random suffix for collision safety
        StringBuilder suffix = new StringBuilder(3);
        for (int i = 0; i < 3; i++) {
            suffix.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        // 4. Combine (Result is typically 11-12 characters)
        return timePart + "-" + suffix.toString();
    }
}
