package com.warehouseforgoods.warehouseforgoodsbackend.Controller;


import com.warehouseforgoods.warehouseforgoodsbackend.Model.Product;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.ProductRepository;
import com.warehouseforgoods.warehouseforgoodsbackend.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
        if(id==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Product product = productService.getById(id);

        if(product==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(product==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        productService.save(product);
        return new ResponseEntity<>(product,httpHeaders,HttpStatus.CREATED);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, UriComponentsBuilder builder){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(product==null){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        productService.save(product);

        return new ResponseEntity<>(product,httpHeaders,HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id){
        Product product = productService.getById(id);

        if(product==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        productService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.getAll();

        if(products.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(products,HttpStatus.OK);
    }

}
