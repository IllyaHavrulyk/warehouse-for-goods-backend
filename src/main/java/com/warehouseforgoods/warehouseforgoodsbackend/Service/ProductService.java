package com.warehouseforgoods.warehouseforgoodsbackend.Service;

import com.warehouseforgoods.warehouseforgoodsbackend.Model.Product;

import java.util.List;

public interface ProductService {
    Product getById(Long id);

    void save(Product product);

    void delete(Long id);

    List<Product> getAll();
}
