package com.wms.warehouse_management_system.services;

import com.wms.warehouse_management_system.dtos.ProductResponseDto;
import com.wms.warehouse_management_system.entities.Product;
import com.wms.warehouse_management_system.mapper.ProductMapper;
import com.wms.warehouse_management_system.repositorys.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final  ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    // Create the product
    public ProductResponseDto createProduct(Product product){
        Product savedProduct = productRepository.save(product);
        return productMapper.mapEntityToProductResponseDto(savedProduct);
    }

    @Transactional(readOnly = true)
    //Get all product
    public List<ProductResponseDto> getAllProducts(){

        return productRepository.findAll().stream().map(productMapper::mapEntityToProductResponseDto).toList();
    }

    @Transactional(readOnly = true)
    //Get product By Id
    public ProductResponseDto getProductById(Long productId){
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null){
            return productMapper.mapEntityToProductResponseDto(product);
        }
        return null;
    }

    @Transactional
    //Delete product by id
    public void deleteProduct(Long productId){
        productRepository.deleteById(productId);
    }

    @Transactional
    //update product
    public ProductResponseDto updateProduct(Long productId, Product productDetails) {
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
            product.setPrice(productDetails.getPrice());
            product.setWeight(productDetails.getWeight());
            product.setImageUrl(productDetails.getImageUrl());
            Product savedProduct = productRepository.save(product);
            return productMapper.mapEntityToProductResponseDto(savedProduct);
        }
        return null;
    }
}
