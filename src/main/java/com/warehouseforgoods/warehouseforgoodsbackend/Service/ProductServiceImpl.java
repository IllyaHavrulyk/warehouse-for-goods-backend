package com.warehouseforgoods.warehouseforgoodsbackend.Service;

import com.warehouseforgoods.warehouseforgoodsbackend.Error.ProductExceptions;
import com.warehouseforgoods.warehouseforgoodsbackend.Error.ProductNotFoundException;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.Product;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product getById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductExceptions(ProductExceptions.Error.PRODUCT_DAO_GET_FAILED));
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
        //       throw new ProductExceptions(ProductExceptions.Error.PRODUCT_DAO_CREATE_FAILED);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = productRepository.findAll();

        if(products.isEmpty()){
            throw  new ProductExceptions(ProductExceptions.Error.PRODUCT_DAO_LIST_FAILED);
        }

        return products;
    }
}
