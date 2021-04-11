package com.warehouseforgoods.warehouseforgoodsbackend.Service;

import com.warehouseforgoods.warehouseforgoodsbackend.Model.Stats;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.StatsRepository;
import java.util.List;

import com.warehouseforgoods.warehouseforgoodsbackend.Utills.StatsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements StatsService{

  @Autowired
  private StatsRepository statsRepository;

  @Override
  public List<Stats> getAll() {
    Stats stats = statsRepository.findAll().get(0);
    stats.setWarehousesCreatedPerEveryMonth(StatsUtils.sortMapByMonths(stats.getWarehousesCreatedPerEveryMonth()));
    List<Stats> statsList = statsRepository.findAll();
    statsList.set(0,stats);
    return statsList;
  }
}
