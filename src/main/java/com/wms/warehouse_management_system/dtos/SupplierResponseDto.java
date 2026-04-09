package com.wms.warehouse_management_system.dtos;

import lombok.Data;

import java.util.List;

@Data
public class SupplierResponseDto {

    private  Long supplierId;

    private String name;

    private String email;

    private String phone;

    private String address1;

    private String address2;

    private String city;

    private String country;

    private String postalCode;

    private List<SupplierItemResponseDto> supplierItems;
}
