package com.warehouseforgoods.warehouseforgoodsbackend.Controller;

import com.warehouseforgoods.warehouseforgoodsbackend.Model.Product;
import com.warehouseforgoods.warehouseforgoodsbackend.Service.ProductService;
import com.warehouseforgoods.warehouseforgoodsbackend.Utills.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
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
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        HttpHeaders httpHeaders = new HttpHeaders();
        productService.save(product);
        return new ResponseEntity<>(product,httpHeaders,HttpStatus.CREATED);
    }

    @PostMapping(value="/update/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct(@RequestBody Product product,@PathVariable("id") Long id){
        HttpHeaders httpHeaders = new HttpHeaders();

        Product sourceProduct = productService.getById(id);
        ObjectMapper.map(sourceProduct,product);
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
    @GetMapping(value="/search/{searchValue}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> searchProduct(@PathVariable("searchValue") String searchValue){
        List<Product> products = productService.search(searchValue);

        return new ResponseEntity<>(products,HttpStatus.OK);
    }
    @GetMapping(value="/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> filterProduct(
            @RequestParam("minPrice")BigDecimal minPrice,
            @RequestParam("maxPrice")BigDecimal maxPrice){
        List<Product> products = productService.filter(minPrice,maxPrice);

        return new ResponseEntity<>(products,HttpStatus.OK);
    }

}
