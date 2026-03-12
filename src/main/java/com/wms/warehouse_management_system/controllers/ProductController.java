package com.wms.warehouse_management_system.controllers;

import com.wms.warehouse_management_system.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private  ProductService productService;
}
