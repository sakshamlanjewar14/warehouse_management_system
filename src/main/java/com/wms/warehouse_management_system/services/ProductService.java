package com.wms.warehouse_management_system.services;

import com.wms.warehouse_management_system.entities.Product;
import com.wms.warehouse_management_system.repositorys.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Create the product
    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    //Get all product
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    //Get product By Id
    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    //Delete product by id
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
