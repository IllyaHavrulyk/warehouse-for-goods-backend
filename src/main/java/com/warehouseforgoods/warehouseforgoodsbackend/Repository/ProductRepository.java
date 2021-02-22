package com.warehouseforgoods.warehouseforgoodsbackend.Repository;

import com.warehouseforgoods.warehouseforgoodsbackend.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
