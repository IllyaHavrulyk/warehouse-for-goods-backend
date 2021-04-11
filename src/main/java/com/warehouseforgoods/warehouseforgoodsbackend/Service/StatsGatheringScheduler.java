package com.warehouseforgoods.warehouseforgoodsbackend.Service;

import com.warehouseforgoods.warehouseforgoodsbackend.Model.Product;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.Stats;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.Warehouse;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.ProductRepository;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.StatsRepository;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.UserRepository;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.WarehouseRepository;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StatsGatheringScheduler {


  private static final Logger log = LoggerFactory.getLogger(StatsGatheringScheduler.class);

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private WarehouseRepository warehouseRepository;

  @Autowired
  private StatsRepository statsRepository;

  @Scheduled(fixedRate = 5000)
  public void generateWarehousesCreatedPerEveryMonth() {
    Map<String, Integer> warehousesCreatedPerEveryMonth = new LinkedHashMap<>();

    if(!warehouseRepository.findAll().isEmpty()){
      List<Warehouse> allWarehouses = warehouseRepository.findAll().stream()
          .filter(warehouse -> warehouse.getDateAdded().isAfter(LocalDateTime.now().minusMonths(6)))
          .sorted(Comparator.comparing(Warehouse::getDateAdded))
          .peek(warehouse -> {
            Month month = warehouse.getDateAdded().getMonth();
            if(warehousesCreatedPerEveryMonth.containsKey(month.name())){
              warehousesCreatedPerEveryMonth.put(month.name(), warehousesCreatedPerEveryMonth.get(month.name()) + 1);
            }else{
              warehousesCreatedPerEveryMonth.put(month.name(), 1);
            }

          })
          .collect(Collectors.toList());
      saveOrUpdateWarehousesPerEveryMonth(warehousesCreatedPerEveryMonth);
      log.info("Warehouses created in last 6 months - {}", allWarehouses.size());
    }else{
      log.info("There are no created warehouses in last 6 months.");
    }
  }

  @Scheduled(fixedDelay = 5000)
  public void generateProductsCreatedPerMonth(){
    Map<String, Integer> productsCreatedPerEveryMonth = new LinkedHashMap<>();

    if(!productRepository.findAll().isEmpty()){
      List<Product> allWarehouses = productRepository.findAll().stream()
              .filter(product -> product.getDateAdded().isAfter(LocalDateTime.now().minusMonths(6)))
              .sorted(Comparator.comparing(Product::getDateAdded))
              .peek(product -> {
                Month month = product.getDateAdded().getMonth();
                if(productsCreatedPerEveryMonth.containsKey(month.name())){
                  productsCreatedPerEveryMonth.put(month.name(), productsCreatedPerEveryMonth.get(month.name()) + 1);
                }else{
                  productsCreatedPerEveryMonth.put(month.name(), 1);
                }

              })
              .collect(Collectors.toList());
      saveOrUpdateProductsPerEveryMonth(productsCreatedPerEveryMonth);
      log.info("Products created in last 6 months - {}", allWarehouses.size());
    }else{
      log.info("There are no created products in last 6 months.");
    }
  }

  private void saveOrUpdateProductsPerEveryMonth(Map<String , Integer> productsCreatedPerEveryMonth){
    Stats stats = new Stats();
    stats.setProductsCreatedPerEveryMonth(productsCreatedPerEveryMonth);
    List<Stats> allStats = statsRepository.findAll();
    if(allStats.isEmpty()){
      statsRepository.save(stats);
    }else{
      Stats statsToUpdate = allStats.get(0);
      statsToUpdate.setProductsCreatedPerEveryMonth(productsCreatedPerEveryMonth);
      statsRepository.save(statsToUpdate);
    }
  }

  private void saveOrUpdateWarehousesPerEveryMonth(Map<String, Integer> warehousesCreatedPerEveryMonth){
    Stats stats = new Stats();
    stats.setWarehousesCreatedPerEveryMonth(warehousesCreatedPerEveryMonth);
    List<Stats> allStats = statsRepository.findAll();
    if(allStats.isEmpty()){
      statsRepository.save(stats);
    }else{
      Stats statsToUpdate = allStats.get(0);
      statsToUpdate.setWarehousesCreatedPerEveryMonth(warehousesCreatedPerEveryMonth);
      statsRepository.save(statsToUpdate);
    }
  }

}
