package com.wms.warehouse_management_system.services;

import com.wms.warehouse_management_system.dtos.StockTransferItemRequestDto;
import com.wms.warehouse_management_system.dtos.StockTransferItemResponseDto;
import com.wms.warehouse_management_system.dtos.StockTransferRequestDto;
import com.wms.warehouse_management_system.dtos.StockTransferResponseDto;
import com.wms.warehouse_management_system.entities.Product;
import com.wms.warehouse_management_system.entities.StockTransfer;
import com.wms.warehouse_management_system.entities.StockTransferItem;
import com.wms.warehouse_management_system.entities.Warehouse;
import com.wms.warehouse_management_system.enums.TransferStatus;
import com.wms.warehouse_management_system.mapper.StockTransferMapper;
import com.wms.warehouse_management_system.repositorys.ProductRepository;
import com.wms.warehouse_management_system.repositorys.StokTransferItemRepository;
import com.wms.warehouse_management_system.repositorys.StokTransferRepository;
import com.wms.warehouse_management_system.repositorys.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class StockTransferService {

    private final StokTransferRepository stokTransferRepository;
    private final StokTransferItemRepository stokTransferItemRepository;
    private final StockTransferMapper stockTransferMapper;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

//    Create Stock transfer
    @Transactional
    public StockTransferResponseDto createStockTransfer(StockTransferRequestDto requestDto){
        try{
            //setting status
            requestDto.setStatus(TransferStatus.DRAFT);
            // get source warehouse
            Warehouse sourceW = warehouseRepository.getReferenceById(requestDto.getSourceWarehouseId());
            // get destination warehouse
            Warehouse destinationW = warehouseRepository.getReferenceById(requestDto.getDestinationWarehouseId());

            StockTransfer stockTransfer = stockTransferMapper.mapRequestDtoToStockTransferEntity(requestDto, sourceW, destinationW);

            // processing stockTransfer items
            List<StockTransferItem> stockTransferItems = new ArrayList<>();
            for(StockTransferItemRequestDto itemRequestDto : requestDto.getStockTransferItems()){
                Product p = productRepository.getReferenceById(itemRequestDto.getProductId());
                StockTransferItem stockTransferItem = stockTransferMapper.mapRequestDtoToStockTransferItemEntity(itemRequestDto, stockTransfer, p);
                stockTransferItems.add(stockTransferItem);
            }
            stockTransfer.setStockTransferItems(stockTransferItems);

            // store stockTransfer
            StockTransfer savedStockTransfer = stokTransferRepository.save(stockTransfer);

            // store stockTransfer items
            stokTransferItemRepository.saveAll(stockTransferItems);

            return stockTransferMapper.mapEntityToStockTransferResponseDto(savedStockTransfer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    get all transfer
    @Transactional(readOnly = true)
    public List<StockTransferResponseDto> getAllStockTransfer(){
        List<StockTransfer> stockTransfers = stokTransferRepository.findAll();
        return stockTransfers
                .stream()
                .map(stockTransferMapper::mapEntityToStockTransferResponseDto).toList();
    }

//    get all transfer by id
    @Transactional
    public StockTransferResponseDto getStockTransferById(long id){
        StockTransfer stockTransfer = stokTransferRepository.findById(id).orElse(null);
        if (stockTransfer != null){
            return  stockTransferMapper.mapEntityToStockTransferResponseDto(stockTransfer);
        }
        return null;
    }

//    delete transfer
    @Transactional
    public void deleteStockTransfer(Long id){
        stokTransferRepository.deleteById(id);
    }

//  update transfer
    @Transactional
    public StockTransferResponseDto updateStockTransfer(Long id, StockTransferRequestDto requestDto){
        if (!Objects.equals(id, requestDto.getId())){
            return null;
        }
        StockTransfer transfer = stokTransferRepository.findById(id).orElse(null);
        if (transfer != null){
            Warehouse sourceW = warehouseRepository.getReferenceById(requestDto.getSourceWarehouseId());
            Warehouse destinationW = warehouseRepository.getReferenceById(requestDto.getDestinationWarehouseId());
            transfer.setSourceWarehouse(sourceW);
            transfer.setDestinationWarehouse(destinationW);
            transfer.setStatus(requestDto.getStatus());
            StockTransfer updateStockTransfer = stokTransferRepository.save(transfer);
            return stockTransferMapper.mapEntityToStockTransferResponseDto(updateStockTransfer);
        }
        return null;
    }

//    update transfer items
    @Transactional
    public List<StockTransferItemResponseDto> updateStockTransferItems(List<StockTransferItemRequestDto> stockTransferItemRequestDtos, Long stockTransferId){
        StockTransfer transfer = stokTransferRepository.findById(stockTransferId).orElse(null);
        if (transfer != null){
            stockTransferItemRequestDtos.forEach(item->{
                Product product = productRepository.getReferenceById(item.getProductId());
                StockTransferItem stockTransferItem = stokTransferItemRepository.findById(item.getId()).orElse(null);
                if (stockTransferItem != null){
                    stockTransferItem.setQuantity(item.getQuantity());
                    stockTransferItem.setProduct(product);
                    stokTransferItemRepository.save(stockTransferItem);
                }
            });
        }
        return null;
    }
}
