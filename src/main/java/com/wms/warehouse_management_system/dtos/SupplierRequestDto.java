package com.wms.warehouse_management_system.dtos;

import lombok.Data;

import java.util.List;

@Data
public class SupplierRequestDto {

    private  Long supplierId;

    private String name;

    private String supplierCode;

    private String email;

    private String phone;

    private String address1;

    private String address2;

    private String city;

    private String state;

    private String country;

    private String postalCode;

    private List<SupplierItemRequestDto> supplierItems;
}
