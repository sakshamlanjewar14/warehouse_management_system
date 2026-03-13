package com.wms.warehouse_management_system.services;

import com.wms.warehouse_management_system.entities.Product;
import com.wms.warehouse_management_system.repositorys.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
    public Product getProductById(Long productId){
        return productRepository.findById(productId).orElse(null);
    }

    //Delete product by id
    public void deleteProduct(Long productId){
        productRepository.deleteById(productId);
    }

    public Product updateProduct(Long productId, Product productDetails) {
//        Checking the productId or  productDetailsId
        if(!Objects.equals(productId, productDetails.getProductId())){
            return null;
        }
        Product product = productRepository.findById(productId).orElse(null);

        if (product != null){
            product.setName(productDetails.getName());
            product.setSku(productDetails.getSku());
            product.setDescription(productDetails.getDescription());
            product.setBarcode(productDetails.getBarcode());
            return productRepository.save(product);
        }
        return null;
    }
}
