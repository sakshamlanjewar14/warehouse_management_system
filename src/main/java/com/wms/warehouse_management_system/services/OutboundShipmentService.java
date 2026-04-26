package com.wms.warehouse_management_system.services;

import com.wms.warehouse_management_system.dtos.*;
import com.wms.warehouse_management_system.entities.InboundShipment;
import com.wms.warehouse_management_system.entities.OutboundShipment;
import com.wms.warehouse_management_system.entities.OutboundShipmentItem;
import com.wms.warehouse_management_system.entities.Product;
import com.wms.warehouse_management_system.enums.OutboundShipmentStatus;
import com.wms.warehouse_management_system.mapper.OutboundShipmentMapper;
import com.wms.warehouse_management_system.repositorys.OutboundShipmentItemRepository;
import com.wms.warehouse_management_system.repositorys.OutboundShipmentRepository;
import com.wms.warehouse_management_system.repositorys.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;



@RequiredArgsConstructor
@Service
public class OutboundShipmentService {

    private final OutboundShipmentRepository outboundShipmentRepository;
    private final OutboundShipmentItemRepository outboundShipmentItemRepository;
    private final ProductRepository productRepository;
    private final OutboundShipmentMapper outboundShipmentMapper;

//    create shipment
    @Transactional
    public OutboundShipmentResponseDto createShipment(OutboundShipmentRequestDto requestDto){
        try{
            List<Long> productIdList = requestDto.getOutboundShipmentItems().stream().mapToLong(OutboundShipmentItemRequestDto::getProductId).boxed().toList();
            List<Product> products = productRepository.findAllById(productIdList);
            requestDto.setStatus(OutboundShipmentStatus.CREATED);
            OutboundShipment outboundShipment = outboundShipmentMapper.mapRequestDtoToOutboundShipmentEntity(requestDto, products);
            OutboundShipment savedOutboundShipment = outboundShipmentRepository.save(outboundShipment);
            return  outboundShipmentMapper.mapEntityToOutboundShipmentResponseDto(savedOutboundShipment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    get all shipment
    @Transactional(readOnly = true)
    public List<OutboundShipmentResponseDto> getAllShipment(){
        List<OutboundShipment> outboundShipments = outboundShipmentRepository.findAll();
        return  outboundShipments
                .stream()
                .map(outboundShipmentMapper::mapEntityToOutboundShipmentResponseDto).toList();
    }

//    get shipment by id
    @Transactional(readOnly = true)
    public  OutboundShipmentResponseDto getShipmentByid(long shipmentId){
        OutboundShipment outboundShipment = outboundShipmentRepository.findById(shipmentId).orElse(null);
        if (outboundShipment != null){
            return outboundShipmentMapper.mapEntityToOutboundShipmentResponseDto(outboundShipment);
        }
        return null;
    }


//    delete shipment
    public void deleteShipment(Long shipmentId){
        outboundShipmentRepository.deleteById(shipmentId);
    }

//    update shipment
    @Transactional
    public  OutboundShipmentResponseDto updateShipment(Long shipmentId, OutboundShipmentRequestDto requestDto){
        if (!Objects.equals(shipmentId, requestDto.getShipmentId())){
            return null;
        }
        OutboundShipment shipment = outboundShipmentRepository.findById(shipmentId).orElse(null);
        if (shipment != null){
            shipment.setShipmentNumber(requestDto.getShipmentNumber());
            shipment.setCustomerName(requestDto.getCustomerName());
            shipment.setShipmentAddress(requestDto.getShipmentAddress());
            shipment.setStatus(requestDto.getStatus());
            shipment.setTrackingNumber(requestDto.getTrackingNumber());
            OutboundShipment updateOutboundShipment = outboundShipmentRepository.save(shipment);
            return  outboundShipmentMapper.mapEntityToOutboundShipmentResponseDto(updateOutboundShipment);
        }
        return null;
    }

    @Transactional
    public List<OutboundShipmentItemResponseDto> updateShipmentItems(
            List<OutboundShipmentItemRequestDto> outboundShipmentItemRequestDtos,
            Long shipmentId){
        OutboundShipment shipment =  outboundShipmentRepository.findById(shipmentId).orElse(null);
        if (shipment != null){
            outboundShipmentItemRequestDtos.forEach(item-> {
                Product product = productRepository.getReferenceById(item.getProductId());
                OutboundShipmentItem outboundShipmentItem = outboundShipmentItemRepository.findById(item.getShipmentItemId()).orElse(null);
                if(outboundShipmentItem != null){
                    outboundShipmentItem.setQuantity(item.getQuantity());
                    outboundShipmentItem.setProduct(product);
                    outboundShipmentItemRepository.save(outboundShipmentItem);
                }
            });
        }
        return null;
    }
}
