package com.wms.warehouse_management_system.mapper;

import com.wms.warehouse_management_system.dtos.OutboundShipmentItemRequestDto;
import com.wms.warehouse_management_system.dtos.OutboundShipmentItemResponseDto;
import com.wms.warehouse_management_system.dtos.OutboundShipmentRequestDto;
import com.wms.warehouse_management_system.dtos.OutboundShipmentResponseDto;
import com.wms.warehouse_management_system.entities.OutboundShipment;
import com.wms.warehouse_management_system.entities.OutboundShipmentItem;
import com.wms.warehouse_management_system.entities.Product;
import com.wms.warehouse_management_system.enums.OutboundShipmentStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class OutboundShipmentMapper {

//    response for  entity to dto
    public OutboundShipmentResponseDto mapEntityToOutboundShipmentResponseDto(OutboundShipment entity){
        OutboundShipmentResponseDto responseDto = new OutboundShipmentResponseDto();
        responseDto.setShipmentNumber(entity.getShipmentNumber());
        responseDto.setCustomerName(entity.getCustomerName());
        responseDto.setStatus(entity.getStatus());
        responseDto.setShipmentAddress(entity.getShipmentAddress());
        responseDto.setTrackingNumber(entity.getTrackingNumber());
        List<OutboundShipmentItemResponseDto> outboundShipmentItemList = new ArrayList<>();
        if (entity.getOutboundShipmentItems() != null){
            outboundShipmentItemList = entity.getOutboundShipmentItems()
                    .stream()
                    .map(this::mapEntityToOutboundShipmentItemResponseDto)
                    .toList();
        }
        responseDto.setOutboundShipmentItems(outboundShipmentItemList);
        return responseDto;
    }

    public OutboundShipmentItemResponseDto mapEntityToOutboundShipmentItemResponseDto(OutboundShipmentItem entity){
        OutboundShipmentItemResponseDto responseDto = new OutboundShipmentItemResponseDto();
       responseDto.setShipmentId(entity.getOutboundShipment().getShipmentId());
       responseDto.setProductId(entity.getProduct().getProductId());
       responseDto.setProductName(entity.getProduct().getName());
        return responseDto;
    }

    //request for  dto to entity
    public OutboundShipment mapRequestDtoToOutboundShipmentEntity(OutboundShipmentRequestDto requestDto, List<Product> products){
        OutboundShipment outboundShipment = new OutboundShipment();
        outboundShipment.setShipmentNumber(requestDto.getShipmentNumber());
        outboundShipment.setCustomerName(requestDto.getCustomerName());
        outboundShipment.setStatus(OutboundShipmentStatus.valueOf(String.valueOf(requestDto.getStatus())));
        outboundShipment.setShipmentAddress(requestDto.getShipmentAddress());
        outboundShipment.setTrackingNumber(requestDto.getTrackingNumber());
        List<OutboundShipmentItem> outboundShipmentItemList =  new ArrayList<>();
        if (requestDto.getOutboundShipmentItems() != null){
            outboundShipmentItemList = requestDto.getOutboundShipmentItems()
                    .stream().map((itm) ->{
                        Product product = products.stream().filter(p-> Objects.equals(p.getProductId(), itm.getProductId())).findFirst().orElse(null);
                        return this.mapRequestDtoToOutboundShipmentItemEntity(itm, outboundShipment, product);
                    })
                    .toList();
        }
        outboundShipment.setOutboundShipmentItems(outboundShipmentItemList);
        return outboundShipment;
    }

    public OutboundShipmentItem mapRequestDtoToOutboundShipmentItemEntity(OutboundShipmentItemRequestDto requestDto,
                                                                          OutboundShipment outboundShipment, Product product){
        OutboundShipmentItem outboundShipmentItem = new OutboundShipmentItem();
        outboundShipmentItem.setQuantity(requestDto.getQuantity());
        outboundShipmentItem.setShipmentItemId(requestDto.getShipmentItemId());
        outboundShipmentItem.setProduct(product);
        outboundShipmentItem.setOutboundShipment(outboundShipment);
        return outboundShipmentItem;
    }

}
