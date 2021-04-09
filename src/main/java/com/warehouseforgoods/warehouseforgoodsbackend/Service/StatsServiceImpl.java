package com.warehouseforgoods.warehouseforgoodsbackend.Service;

import com.warehouseforgoods.warehouseforgoodsbackend.Model.Stats;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.StatsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements StatsService{

  @Autowired
  private StatsRepository statsRepository;

  @Override
  public List<Stats> getAll() {
    return statsRepository.findAll();
  }
}
