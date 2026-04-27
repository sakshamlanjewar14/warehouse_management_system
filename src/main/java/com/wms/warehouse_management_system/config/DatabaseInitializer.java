package com.wms.warehouse_management_system.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wms.warehouse_management_system.dtos.InventoryItemRequestDto;
import com.wms.warehouse_management_system.dtos.InventoryItemResponseDto;
import com.wms.warehouse_management_system.dtos.StorageBinResponseDto;
import com.wms.warehouse_management_system.dtos.SupplierRequestDto;
import com.wms.warehouse_management_system.entities.InventoryItem;
import com.wms.warehouse_management_system.entities.Product;
import com.wms.warehouse_management_system.entities.StorageBin;
import com.wms.warehouse_management_system.entities.Warehouse;
import com.wms.warehouse_management_system.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final ObjectMapper objectMapper;

    private final ProductService productService;
    private final WarehouseService warehouseService;
    private final StorageBinService storageBinService;
    private final InventoryService inventoryService;
    private final SupplierService supplierService;

    @Override
    public void run(String... args) throws Exception {
        generateAndStoreProductsInDB();
        generateAndStoreWarehousesInDB();
        storeInventoryItems();
        generateAndStoreSuppliersInDB();
        System.out.println("Initial data for product, supplier and warehouse created and saved to database.");
    }

    void generateAndStoreProductsInDB() throws IOException {
        if (productService.getAllProducts().isEmpty()) {
            ClassPathResource resource = new ClassPathResource("products.json");
            InputStream inputStream = resource.getInputStream();
            List<Product> products = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<Product>>() {}
            );
            products.forEach(productService::createProduct);
        }
    }

    private double getRandomDoubleValue(double min,double max) {
        return min + (Math.random() * (max - min));
    }

    void generateAndStoreWarehousesInDB() throws IOException {
        if (warehouseService.getAllWarehouses().isEmpty()) {

            ClassPathResource resource = new ClassPathResource("warehouse.json");
            InputStream inputStream = resource.getInputStream();
            List<Warehouse> warehouses = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<Warehouse>>() {}
            );
            warehouses.forEach(warehouse -> {
                if(warehouse.getStorageBins() != null){
                    for(StorageBin storageBin: warehouse.getStorageBins()){
                        storageBin.setWarehouse(warehouse);
                    }
                }
                warehouseService.createWarehouse(warehouse);
            });
        }
    }

    List<StorageBin> generateStorageBinForWarehouse(Warehouse warehouse, Integer binCount) {
        List<StorageBin> storageBinList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < binCount; i++) {
            int randomCapacity = random.nextInt(100) + 1;
            StorageBin storageBin = new StorageBin();
            //storageBin.setBinId("StorageBinId"+(i+1)); no need to set is its auto generated
            storageBin.setBinCode(UUID.randomUUID().toString());
            storageBin.setCapacity(randomCapacity);
            storageBin.setWarehouse(warehouse);
            storageBinList.add(storageBin);
        }
        return storageBinList;
    }

    public void storeInventoryItems(){
        List<Product> products = productService.getAllProducts();
        Random random = new Random();
        for(Product product : products){

            List<InventoryItemRequestDto> inventoryItemRequestDtos = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                List<StorageBinResponseDto> storageBins = storageBinService.getAllBins();
                List<StorageBinResponseDto> copyOfStorageBins = new ArrayList<>(storageBins);
                Collections.shuffle(copyOfStorageBins);
                InventoryItemRequestDto inventoryItemRequestDto = new InventoryItemRequestDto();
                inventoryItemRequestDto.setProductId(product.getProductId());
                StorageBinResponseDto storageBinResponseDto = copyOfStorageBins.get(0);
                inventoryItemRequestDto.setStorageBinId(storageBinResponseDto.getBinId());

                int qty = random.nextInt(0, storageBinResponseDto.getAvailableCapacity()-1);
                inventoryItemRequestDto.setQuantity(qty);
                inventoryItemRequestDtos.add(inventoryItemRequestDto);
            }
            inventoryService.createInventory(inventoryItemRequestDtos);
        }
    }


    void generateAndStoreSuppliersInDB() throws IOException {

        if (supplierService.getAllSupplier().isEmpty()) {

            ClassPathResource resource = new ClassPathResource("suppliers.json");
            InputStream inputStream = resource.getInputStream();

            List<SupplierRequestDto> suppliers = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<SupplierRequestDto>>() {}
            );

            suppliers.forEach(supplierService::createSupplier);

            System.out.println("✅ Suppliers loaded successfully");
        }
    }

}
