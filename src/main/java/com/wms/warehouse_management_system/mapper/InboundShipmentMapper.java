package com.wms.warehouse_management_system.mapper;

import com.wms.warehouse_management_system.dtos.InboundShipmentItemRequestDto;
import com.wms.warehouse_management_system.dtos.InboundShipmentItemResponseDto;
import com.wms.warehouse_management_system.dtos.InboundShipmentRequestDto;
import com.wms.warehouse_management_system.dtos.InboundShipmentResponseDto;
import com.wms.warehouse_management_system.entities.InboundShipment;
import com.wms.warehouse_management_system.entities.InboundShipmentItem;
import com.wms.warehouse_management_system.enums.InboundShipmentItemStatus;
import com.wms.warehouse_management_system.enums.InboundShipmentStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InboundShipmentMapper {

    //response for entity to dto
    public InboundShipmentResponseDto mapEntityToInboundShipmentResponseDto(InboundShipment entity){
        InboundShipmentResponseDto responseDto = new InboundShipmentResponseDto();
        responseDto.setShipmentCode(entity.getShipmentCode());
        responseDto.setSupplierId(entity.getSupplierId());
        responseDto.setStatus(entity.getStatus().toString());
        responseDto.setReceivedDate(entity.getReceivedDate());
        responseDto.setReferenceNumber(entity.getReferenceNumber());
        responseDto.setNotes(entity.getNotes());
        responseDto.setExpectedDate(entity.getExpectedDate());
        List<InboundShipmentItemResponseDto> inboundShipmentItemList = new ArrayList<>();
        if(entity.getInboundShipmentItems() != null){
            inboundShipmentItemList = entity.getInboundShipmentItems()
                    .stream()
                    .map(this::mapEntityToInboundShipmentItemResponseDto)
                    .toList();
        }
        responseDto.setInboundShipmentItems(inboundShipmentItemList);
        return responseDto;
    }

    public InboundShipmentItemResponseDto mapEntityToInboundShipmentItemResponseDto(InboundShipmentItem entity){
        InboundShipmentItemResponseDto responseDto = new InboundShipmentItemResponseDto();
        responseDto.setProductId(entity.getProductId());
        responseDto.setExpectedQty(entity.getExpectedQty());
        responseDto.setReceivedQty(entity.getReceivedQty());
        responseDto.setDamagedQty(entity.getDamagedQty());
        responseDto.setStatus(entity.getStatus().toString());
        return responseDto;
    }

    //request for  dto to entity
    public InboundShipment mapRequestDtoToInboundShipmentEntity(InboundShipmentRequestDto requestDto){
        InboundShipment inboundShipment = new InboundShipment();
        inboundShipment.setShipmentCode(requestDto.getShipmentCode());
        inboundShipment.setSupplierId(requestDto.getSupplierId());
        inboundShipment.setStatus(InboundShipmentStatus.valueOf(requestDto.getStatus()));
        inboundShipment.setReceivedDate(requestDto.getReceivedDate());
        inboundShipment.setReferenceNumber(requestDto.getReferenceNumber());
        inboundShipment.setNotes(requestDto.getNotes());
        inboundShipment.setExpectedDate(requestDto.getExpectedDate());
        List<InboundShipmentItem> inboundShipmentItemList = new ArrayList<>();
        if(requestDto.getInboundShipmentItems() != null){
            inboundShipmentItemList = requestDto.getInboundShipmentItems()
                    .stream()
                    .map(itm -> this.mapRequestDtoToInboundShipmentItemEntity(itm, inboundShipment))
                    .toList();
        }
        inboundShipment.setInboundShipmentItems(inboundShipmentItemList);
        return inboundShipment;
    }

    public InboundShipmentItem mapRequestDtoToInboundShipmentItemEntity(InboundShipmentItemRequestDto requestDto,
                                                                        InboundShipment inboundShipment){
        InboundShipmentItem inboundShipmentItem = new InboundShipmentItem();
        inboundShipmentItem.setProductId(requestDto.getProductId());
        inboundShipmentItem.setExpectedQty(requestDto.getExpectedQty());
        inboundShipmentItem.setReceivedQty(requestDto.getReceivedQty());
        inboundShipmentItem.setDamagedQty(requestDto.getDamagedQty());
        inboundShipmentItem.setStatus(InboundShipmentItemStatus.valueOf(requestDto.getStatus()));
        // relationship mapping
        inboundShipmentItem.setInboundShipment(inboundShipment);
        return inboundShipmentItem;
    }
}
