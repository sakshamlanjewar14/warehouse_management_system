    package com.wms.warehouse_management_system.config;

    import com.fasterxml.jackson.core.type.TypeReference;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.wms.warehouse_management_system.dtos.InventoryItemQuantityRequestDto;
    import com.wms.warehouse_management_system.dtos.InventoryItemRequestDto;
    import com.wms.warehouse_management_system.dtos.StorageBinResponseDto;
    import com.wms.warehouse_management_system.entities.Product;
    import com.wms.warehouse_management_system.entities.StorageBin;
    import com.wms.warehouse_management_system.entities.Warehouse;
    import com.wms.warehouse_management_system.repositorys.ProductRepository;
    import com.wms.warehouse_management_system.services.InventoryService;
    import com.wms.warehouse_management_system.services.ProductService;
    import com.wms.warehouse_management_system.services.StorageBinService;
    import com.wms.warehouse_management_system.services.WarehouseService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.boot.CommandLineRunner;
    import org.springframework.core.io.ClassPathResource;
    import org.springframework.stereotype.Component;

    import java.io.IOException;
    import java.io.InputStream;
    import java.math.BigDecimal;
    import java.util.*;

    @Component
    @RequiredArgsConstructor
    public class DatabaseInitializer implements CommandLineRunner {

        private final ObjectMapper objectMapper;

        private final ProductService productService;
        private final WarehouseService warehouseService;
        private final StorageBinService storageBinService;
        private final InventoryService inventoryService;

        @Override
        public void run(String... args) throws Exception {
            generateAndStoreProductsInDB();
            generateAndStoreWarehousesInDB();
            storeInventoryItems();
            System.out.println("Initial data for product and warehouse created and saved to database.");
        }

        void generateAndStoreProductsInDB() throws IOException {
            if (productService.getAllProducts().isEmpty()) {
                ClassPathResource resource = new ClassPathResource("products.json");
                InputStream inputStream = resource.getInputStream();
                List<Product> products = objectMapper.readValue(inputStream,
                        new TypeReference<List<Product>>() {}
                );
    products.forEach(productService::createProduct);
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

        public void storeInventoryItems(){
            List<Product> products = productService.getAllProducts();

            Random random = new Random();
            for(Product product : products){
                List<StorageBinResponseDto> storageBins = storageBinService.getAllBins();
                List<StorageBinResponseDto> copyOfStorageBins = new ArrayList<>(storageBins);

                InventoryItemRequestDto inventoryItemRequestDto = new InventoryItemRequestDto();
                inventoryItemRequestDto.setProductId(product.getProductId());
                List<InventoryItemQuantityRequestDto> itemQuantityRequestDtos = new ArrayList<>();
                Collections.shuffle(copyOfStorageBins);
                List<StorageBinResponseDto> selectedStorageBins = List.of(copyOfStorageBins.get(0), copyOfStorageBins.get(1));

                for (int j = 0; j < 2; j++) {
                    StorageBinResponseDto storageBinResponseDto = selectedStorageBins.get(j);
                    InventoryItemQuantityRequestDto inventoryItemQuantityRequestDto = new InventoryItemQuantityRequestDto();
                    int qty = random.nextInt(0, storageBinResponseDto.getAvailableCapacity()-1);
                    System.out.println("binId::"+storageBinResponseDto.getBinId()+", qty::"+qty+", getAvailableCapacity::"+(storageBinResponseDto.getAvailableCapacity()-1));
                    inventoryItemQuantityRequestDto.setQuantity(qty);
                    inventoryItemQuantityRequestDto.setStorageBinId(storageBinResponseDto.getBinId());
                    itemQuantityRequestDtos.add(inventoryItemQuantityRequestDto);
                }

                inventoryItemRequestDto.setRows(itemQuantityRequestDtos);
                inventoryService.createInventory(inventoryItemRequestDto);
            }


        }

    }
