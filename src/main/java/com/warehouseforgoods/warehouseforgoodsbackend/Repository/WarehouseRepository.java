package com.warehouseforgoods.warehouseforgoodsbackend.Repository;

import com.warehouseforgoods.warehouseforgoodsbackend.Model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Long> {
}
