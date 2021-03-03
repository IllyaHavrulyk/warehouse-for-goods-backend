package com.warehouseforgoods.warehouseforgoodsbackend.Controller;


import com.warehouseforgoods.warehouseforgoodsbackend.Error.ProductNotFoundException;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.Product;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.ProductRepository;
import com.warehouseforgoods.warehouseforgoodsbackend.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
@Validated
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
        Product product = productService.getById(id);

        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PostMapping(value="/save",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> saveProduct(@Valid @RequestBody Product product){
        HttpHeaders httpHeaders = new HttpHeaders();

        productService.save(product);

        return new ResponseEntity<>(product,httpHeaders,HttpStatus.CREATED);
    }

    @PutMapping(value="/update/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product, UriComponentsBuilder builder){
        HttpHeaders httpHeaders = new HttpHeaders();

        productService.save(product);

        return new ResponseEntity<>(product,httpHeaders,HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id){
        Product product = productService.getById(id);

        productService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value="/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.getAll();

        return new ResponseEntity<>(products,HttpStatus.OK);
    }

}
