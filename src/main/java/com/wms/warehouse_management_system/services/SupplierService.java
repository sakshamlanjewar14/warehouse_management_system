package com.wms.warehouse_management_system.services;

import com.wms.warehouse_management_system.dtos.SupplierItemRequestDto;
import com.wms.warehouse_management_system.dtos.SupplierItemResponseDto;
import com.wms.warehouse_management_system.dtos.SupplierRequestDto;
import com.wms.warehouse_management_system.dtos.SupplierResponseDto;
import com.wms.warehouse_management_system.entities.Supplier;
import com.wms.warehouse_management_system.entities.SupplierItem;
import com.wms.warehouse_management_system.mapper.SupplierMapper;
import com.wms.warehouse_management_system.repositorys.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SupplierService {

    @Autowired
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;


    @Transactional
    public SupplierResponseDto createSupplier(SupplierRequestDto requestDto){
        Supplier supplier = supplierMapper.mapRequestDtoToSupplierEntity(requestDto);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return supplierMapper.mapEntityToSupplierResponseDto(savedSupplier);
    }

    @Transactional(readOnly = true)
    public List<SupplierResponseDto> getAllSupplier(){
        List<Supplier> suppliers = supplierRepository.findAll();
        return suppliers.stream().map(supplierMapper::mapEntityToSupplierResponseDto).toList();
    }

    @Transactional(readOnly = true)
    public SupplierResponseDto getSupplierById(Long supplierId){
        Supplier supplier = supplierRepository.findById(supplierId).orElse(null);
        if (supplier != null){
            return supplierMapper.mapEntityToSupplierResponseDto(supplier);
        }
        return null;
    }

    @Transactional
    public void deleteSupplier(Long supplierId){
        supplierRepository.deleteById(supplierId);
    }


    @Transactional
    public SupplierResponseDto updateSupplier(Long supplierId, SupplierRequestDto requestDto){
        if(!Objects.equals(supplierId, requestDto.getSupplierId())){
            return null;
        }
        Supplier supplier = supplierRepository.findById(supplierId).orElse(null);

        if (supplier != null) {
            supplier.setName(requestDto.getName());
            supplier.setEmail(requestDto.getEmail());
            supplier.setPhone(requestDto.getPhone());
            supplier.setAddress1(requestDto.getAddress1());
            supplier.setAddress2(requestDto.getAddress2());
            supplier.setCity(requestDto.getCity());
            supplier.setCountry(requestDto.getCountry());
            supplier.setPostalCode(requestDto.getPostalCode());
            List<SupplierItemRequestDto> supplierItemDtos = requestDto.getSupplierItems();
            List<SupplierItem> supplierItems = supplierItemDtos.stream().map(si-> supplierMapper.mapRequestDtoToSupplierItemEntity(si, supplier)).toList();
            supplier.setSupplierItems(supplierItems);
            Supplier updatedSupplier = supplierRepository.save(supplier);
            return supplierMapper.mapEntityToSupplierResponseDto(updatedSupplier);
        }
        return null;
    }

//    public List<SupplierItemResponseDto> updateSupplierItem(List<SupplierItemRequestDto> supplierItemRequestDtos,
//                                                            Long supplierId){
//        Supplier supplier = supplierRepository.findById(supplierId).orElse(null);
//        if (supplier != null){
//
//        }
//        return null;
//    }
}
