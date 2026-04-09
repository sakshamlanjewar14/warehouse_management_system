package com.wms.warehouse_management_system.services;

import com.wms.warehouse_management_system.entities.Supplier;
import com.wms.warehouse_management_system.repositorys.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SupplierService {

    @Autowired
    private  SupplierRepository supplierRepository;

    public Supplier createSupplier(Supplier supplier){
        return supplierRepository.save(supplier);
    }

    public List<Supplier> getAllSupplier(){
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(Long supplierId){
        return supplierRepository.findById(supplierId).orElse(null);
    }

    public void deleteSupplier(Long supplierId){
        supplierRepository.deleteById(supplierId);
    }

    public Supplier updateSupplier(Long supplierId, Supplier supplierDetails){
        if(!Objects.equals(supplierId, supplierDetails.getSupplierId())){
            return null;
        }
        Supplier supplier = supplierRepository.findById(supplierId).orElse(null);

        if (supplier != null){
            supplier.setName(supplierDetails.getName());
            supplier.setEmail(supplierDetails.getEmail());
            supplier.setPhone(supplierDetails.getPhone());
            supplier.setAddress(supplierDetails.getAddress());
            supplier.setCity(supplierDetails.getCity());
            supplier.setCountry(supplierDetails.getCountry());
            supplier.setPostalCode(supplierDetails.getPostalCode());
            supplier.setSupplierItems(supplierDetails.getSupplierItems());
            return supplierRepository.save(supplier);
        }
        return null;
    }
}
