package com.warehouseforgoods.warehouseforgoodsbackend.Controller;

import com.warehouseforgoods.warehouseforgoodsbackend.Model.Product;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.User;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.ProductRepository;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.UserRepository;
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
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/product")
@Validated
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
        Product product = productService.getById(id);

        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PostMapping(value="/save",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> saveProduct(
            @RequestBody Product product,
            @RequestParam("warehouseId") Long warehouseId,
            Principal principal){
        HttpHeaders httpHeaders = new HttpHeaders();

        productService.save(product,warehouseId);

        return new ResponseEntity<>(product,httpHeaders,HttpStatus.CREATED);
    }

    @PostMapping(value="/update/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct
            (@RequestBody Product product,
             @PathVariable("id") Long id){
        HttpHeaders httpHeaders = new HttpHeaders();

        Product sourceProduct = productService.getById(id);
        ObjectMapper.map(sourceProduct,product);
        productRepository.save(product);

        return new ResponseEntity<>(product,httpHeaders,HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{warehouseId}/{productId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> deleteProduct
            (@PathVariable("warehouseId") Long warehouseId,
             @PathVariable("productId") Long productId){
        Product product = productService.getById(productId);

        productService.delete(productId,warehouseId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value="/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getAllProducts
            (Principal principal,
             @RequestParam("warehouseId") Long warehouseId
    ){
      //  User currentUser = userRepository.findByUsername(principal.getName()).get();
        List<Product> products = productService.getAll(warehouseId);

        return new ResponseEntity<>(products,HttpStatus.OK);
    }
    @GetMapping(value="/search/{searchValue}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> searchProduct
            (@PathVariable("searchValue") String searchValue,
             @RequestParam("warehouseId") Long warehouseId
            ){
        List<Product> products = productService.search(searchValue,warehouseId);

        return new ResponseEntity<>(products,HttpStatus.OK);
    }
    @GetMapping(value="/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> filterProduct(
            @RequestParam("warehouseId") Long warehouseId,
            @RequestParam("minPrice")BigDecimal minPrice,
            @RequestParam("maxPrice")BigDecimal maxPrice){
        List<Product> products = productService.filter(minPrice,maxPrice,warehouseId);

        return new ResponseEntity<>(products,HttpStatus.OK);
    }

}
