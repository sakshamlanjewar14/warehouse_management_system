package com.wms.warehouse_management_system.services;



import com.wms.warehouse_management_system.dtos.InboundShipmentItemRequestDto;
import com.wms.warehouse_management_system.dtos.InboundShipmentItemResponseDto;
import com.wms.warehouse_management_system.dtos.InboundShipmentRequestDto;
import com.wms.warehouse_management_system.dtos.InboundShipmentResponseDto;
import com.wms.warehouse_management_system.entities.InboundShipment;
import com.wms.warehouse_management_system.enums.InboundShipmentItemStatus;
import com.wms.warehouse_management_system.enums.InboundShipmentStatus;
import com.wms.warehouse_management_system.mapper.InboundShipmentMapper;
import com.wms.warehouse_management_system.repositorys.InboundShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class InboundShipmentService {

    private final InboundShipmentRepository  inboundShipmentRepository;
    private final InboundShipmentMapper inboundShipmentMapper;

    //create the Shipment
    @Transactional
    public InboundShipmentResponseDto createShipment(InboundShipmentRequestDto requestDto){
        try{
            requestDto.setStatus(InboundShipmentStatus.CREATED.toString());
            requestDto.getInboundShipmentItems().forEach(inboundItemDto ->{
                inboundItemDto.setStatus(InboundShipmentItemStatus.PENDING.toString());
            });

            InboundShipment inboundShipment = inboundShipmentMapper.mapRequestDtoToInboundShipmentEntity(requestDto);
            InboundShipment savedInboundShipment = inboundShipmentRepository.save(inboundShipment);
            return inboundShipmentMapper.mapEntityToInboundShipmentResponseDto(savedInboundShipment);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //get all Shipment
    @Transactional(readOnly = true)
    public List<InboundShipmentResponseDto> getAllShipment(){
        List<InboundShipment> inboundShipments = inboundShipmentRepository.findAll();
        return inboundShipments
                .stream()
                .map(inboundShipmentMapper::mapEntityToInboundShipmentResponseDto).toList();

    }

    //get Shipment by id
    @Transactional(readOnly = true)
    public InboundShipmentResponseDto getShipmentById(Long shipmentId){
        InboundShipment inboundShipment = inboundShipmentRepository.findById(shipmentId).orElse(null);
        if(inboundShipment != null){
            return inboundShipmentMapper.mapEntityToInboundShipmentResponseDto(inboundShipment);
        }
        return null;
    }

    //delete Shipment
    @Transactional
    public void deleteShipment(Long shipmentId){
        inboundShipmentRepository.deleteById(shipmentId);
    }

    //update Shipment
    @Transactional
    public InboundShipmentResponseDto updateShipment(Long shipmentId, InboundShipmentRequestDto requestDto){
        if (!Objects.equals(shipmentId, requestDto.getShipmentId())){
            return null;
        }
        InboundShipment shipment =  inboundShipmentRepository.findById(shipmentId).orElse(null);

        if (shipment  != null){
            shipment.setShipmentCode(requestDto.getShipmentCode());
            shipment.setSupplierId(requestDto.getSupplierId());
            shipment.setExpectedDate(requestDto.getExpectedDate());
            shipment.setStatus(InboundShipmentStatus.valueOf(requestDto.getStatus()));
            shipment.setReferenceNumber(requestDto.getReferenceNumber());
            shipment.setNotes(requestDto.getNotes());
            InboundShipment updatedInboundShipment = inboundShipmentRepository.save(shipment);
            return inboundShipmentMapper.mapEntityToInboundShipmentResponseDto(updatedInboundShipment);
        }
        return null;
    }

    @Transactional
    public List<InboundShipmentItemResponseDto> updateShipmentItems(
            List<InboundShipmentItemRequestDto> inboundShipmentItemRequestDtos,
            Long shipmentId){
        InboundShipment shipment =  inboundShipmentRepository.findById(shipmentId).orElse(null);
        if (shipment  != null){

        }
        return null;
    }
}
