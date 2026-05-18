package com.wms.warehouse_management_system.services;

import com.wms.warehouse_management_system.dtos.ProductRequestDto;
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
    public ProductResponseDto createProduct(ProductRequestDto requestDto){
        Product product = productMapper.mapRequestDtoToProductEntity(requestDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.mapEntityToProductResponseDto(savedProduct);
    }

    @Transactional(readOnly = true)
    //Get all product
    public List<ProductResponseDto> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::mapEntityToProductResponseDto).toList();
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
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto requestDto) {
//        Checking the productId or  productDetailsId
        if(!Objects.equals(productId, requestDto.getProductId())){
            return null;
        }
        Product product = productRepository.findById(productId).orElse(null);

        if (product != null){
            product.setName(requestDto.getName());
            product.setSku(requestDto.getSku());
            product.setDescription(requestDto.getDescription());
            product.setBarcode(requestDto.getBarcode());
            product.setPrice(requestDto.getPrice());
            product.setWeight(requestDto.getWeight());
            product.setImageUrl(requestDto.getImageUrl());
            Product savedProduct = productRepository.save(product);
            return productMapper.mapEntityToProductResponseDto(savedProduct);
        }
        return null;
    }
}
