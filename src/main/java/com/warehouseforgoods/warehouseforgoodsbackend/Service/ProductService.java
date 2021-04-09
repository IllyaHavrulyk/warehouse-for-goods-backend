package com.warehouseforgoods.warehouseforgoodsbackend.Service;

import com.warehouseforgoods.warehouseforgoodsbackend.Model.Product;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.User;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Product getById(Long warehouseId);

    void save(Product product, Long warehouseId);

    void delete(Long productId,Long warehouseId);

    List<Product> getAll(Long warehouseId);

    List<Product> search(String searchValue,Long warehouseId);

    List<Product> filter(BigDecimal minPrice, BigDecimal maxPrice,Long warehouseId);
}
