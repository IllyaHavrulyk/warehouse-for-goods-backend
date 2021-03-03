package com.warehouseforgoods.warehouseforgoodsbackend.Service;

import com.warehouseforgoods.warehouseforgoodsbackend.Model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Product getById(Long id);

    void save(Product product);

    void delete(Long id);

    List<Product> getAll();

    List<Product> search(String searchValue);

    List<Product> filter(BigDecimal minPrice, BigDecimal maxPrice);
}
