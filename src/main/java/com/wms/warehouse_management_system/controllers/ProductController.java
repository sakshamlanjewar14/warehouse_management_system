package com.wms.warehouse_management_system.controllers;

import com.wms.warehouse_management_system.common.ApiResponse;
import com.wms.warehouse_management_system.dtos.ProductResponseDto;
import com.wms.warehouse_management_system.entities.Product;
import com.wms.warehouse_management_system.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {

        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(@RequestBody Product product){
        System.out.println("product::"+product);
        try{
            ProductResponseDto savedProduct = productService.createProduct(product);
            return ResponseEntity.ok(ApiResponse.success(savedProduct));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAllProduct(){
        List<ProductResponseDto> productsList = productService.getAllProducts();
        if(productsList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Products Not Found"));
        }
        return ResponseEntity.ok(ApiResponse.success(productsList));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProductById(@PathVariable("productId") Long productId){
        ProductResponseDto product = productService.getProductById(productId);
        if(product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Product Not Found For Id :"+ productId));
        }
        return ResponseEntity.ok(ApiResponse.success(product));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable("productId") Long productId){
        try{
            productService.deleteProduct(productId);
            return ResponseEntity.ok(ApiResponse.success("Product with id :"+ productId + " deleted successfully"));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProduct(@PathVariable("productId") Long productId, @RequestBody Product product){
        try {
            ProductResponseDto updatedProduct = productService.updateProduct(productId,product);
            if(updatedProduct == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Product not found"));
            }
            return ResponseEntity.ok(ApiResponse.success(updatedProduct));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

}
