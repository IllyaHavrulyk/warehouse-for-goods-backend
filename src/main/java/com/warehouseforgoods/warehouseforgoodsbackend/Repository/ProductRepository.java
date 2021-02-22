package com.warehouseforgoods.warehouseforgoodsbackend.Repository;

import com.warehouseforgoods.warehouseforgoodsbackend.Model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
