package com.warehouseforgoods.warehouseforgoodsbackend.Repository;

import com.warehouseforgoods.warehouseforgoodsbackend.Model.Stats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Long> {

}
