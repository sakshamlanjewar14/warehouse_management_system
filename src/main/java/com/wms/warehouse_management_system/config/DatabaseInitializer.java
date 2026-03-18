package com.wms.warehouse_management_system.config;

import com.wms.warehouse_management_system.entities.Product;
import com.wms.warehouse_management_system.entities.StorageBin;
import com.wms.warehouse_management_system.entities.Warehouse;
import com.wms.warehouse_management_system.services.InventoryService;
import com.wms.warehouse_management_system.services.ProductService;
import com.wms.warehouse_management_system.services.StorageBinService;
import com.wms.warehouse_management_system.services.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final ProductService productService;
    private final WarehouseService warehouseService;
    private final StorageBinService storageBinService;
    private final InventoryService inventoryService;

    @Override
    public void run(String... args) throws Exception {
        generateAndStoreProductsInDB();
        generateAndStoreWarehousesInDB();
        System.out.println("Initial data for product and warehouse created and saved to database.");
    }

    void generateAndStoreProductsInDB() {
        if (productService.getAllProducts().isEmpty()) {
            for (int i = 0; i < 10; i++) {
                Product product = new Product();
                product.setName("Product " + (i + 1));
                product.setSku("sku-" + (i + 1));
                product.setDescription("Product description " + (i + 1));
                product.setBarcode(String.valueOf(i + 1000000));
                product.setPrice(BigDecimal.valueOf(getRandomDoubleValue(100.0,100000.0)));
                product.setWeight(getRandomDoubleValue(100.0,1000.0));
                product.setImageUrl("https://placehold.jp/24/3a4a5b/ffffff/400x400.png?text=No+Product+Image");
                productService.createProduct(product);
            }
        }
    }

    private double getRandomDoubleValue(double min,double max) {
       return min + (Math.random() * (max - min));
    }

    void generateAndStoreWarehousesInDB() {
        if (warehouseService.getAllWarehouses().isEmpty()) {
            for (int i = 0; i < 5; i++) {
                Warehouse warehouse = new Warehouse();
                warehouse.setName("Warehouse " + (i + 1));
                warehouse.setLocation("Location-" + (i + 1));
                warehouse.setCapacity(0);
                Warehouse savedWarehouse = warehouseService.createWarehouse(warehouse);
                List<StorageBin> storageBinList = generateStorageBinForWarehouse(savedWarehouse, (i + 2));
                storageBinList.forEach(storageBin -> storageBinService.createBin(savedWarehouse.getWarehouseId(), storageBin));
            }
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


}
