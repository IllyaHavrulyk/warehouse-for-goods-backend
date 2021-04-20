package com.warehouseforgoods.warehouseforgoodsbackend.Service;

import com.warehouseforgoods.warehouseforgoodsbackend.Model.Product;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.Stats;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.User;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.Warehouse;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.ProductRepository;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.StatsRepository;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.UserRepository;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.WarehouseRepository;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import net.bytebuddy.dynamic.scaffold.MethodGraph.Linked;
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

    if (!warehouseRepository.findAll().isEmpty()) {
      List<Warehouse> allWarehouses = warehouseRepository.findAll().stream()
          .filter(warehouse -> warehouse.getDateAdded().isAfter(LocalDateTime.now().minusMonths(6)))
          .sorted(Comparator.comparing(Warehouse::getDateAdded))
          .peek(warehouse -> {
            Month month = warehouse.getDateAdded().getMonth();
            if (warehousesCreatedPerEveryMonth.containsKey(month.name())) {
              warehousesCreatedPerEveryMonth.put(month.name(), warehousesCreatedPerEveryMonth.get(month.name()) + 1);
            } else {
              warehousesCreatedPerEveryMonth.put(month.name(), 1);
            }

          })
          .collect(Collectors.toList());
      saveOrUpdateWarehousesPerEveryMonth(warehousesCreatedPerEveryMonth);
      log.info("Warehouses created in last 6 months - {}", allWarehouses.size());
    } else {
      log.info("There are no created warehouses in last 6 months.");
    }
  }

  @Scheduled(fixedDelay = 5000)
  public void generateProductsCreatedPerMonth() {
    Map<String, Integer> productsCreatedPerEveryMonth = new LinkedHashMap<>();

    if (!productRepository.findAll().isEmpty()) {
      List<Product> allWarehouses = productRepository.findAll().stream()
          .filter(product -> product.getDateAdded().isAfter(LocalDateTime.now().minusMonths(6)))
          .sorted(Comparator.comparing(Product::getDateAdded))
          .peek(product -> {
            Month month = product.getDateAdded().getMonth();
            if (productsCreatedPerEveryMonth.containsKey(month.name())) {
              productsCreatedPerEveryMonth.put(month.name(), productsCreatedPerEveryMonth.get(month.name()) + 1);
            } else {
              productsCreatedPerEveryMonth.put(month.name(), 1);
            }

          })
          .collect(Collectors.toList());
      saveOrUpdateProductsPerEveryMonth(productsCreatedPerEveryMonth);
      log.info("Products created in last 6 months - {}", allWarehouses.size());
    } else {
      log.info("There are no created products in last 6 months.");
    }
  }

  @Scheduled(fixedDelay = 5000)
  public void generateUsersRegisteredPerEveryMonth() {
    Map<String, Integer> usersRegisteredPerEveryMonth = new LinkedHashMap<>();

    if (!userRepository.findAll().isEmpty()) {
      List<User> allWarehouses = userRepository.findAll().stream()
          .filter(user -> user.getRegisteredAt().isAfter(LocalDateTime.now().minusMonths(6)))
          .sorted(Comparator.comparing(User::getRegisteredAt))
          .peek(user -> {
            Month month = user.getRegisteredAt().getMonth();
            if (usersRegisteredPerEveryMonth.containsKey(month.name())) {
              usersRegisteredPerEveryMonth.put(month.name(), usersRegisteredPerEveryMonth.get(month.name()) + 1);
            } else {
              usersRegisteredPerEveryMonth.put(month.name(), 1);
            }

          })
          .collect(Collectors.toList());
      saveOrUpdateUsersRegisteredPerEveryMonth(usersRegisteredPerEveryMonth);
      log.info("Users registered in last 6 months - {}", allWarehouses.size());
    } else {
      log.info("There are no registered users in last 6 months.");
    }
  }

  @Scheduled(fixedDelay = 5000)
  public void generateAverageWarehousesPerUser() {
    LinkedHashMap<String, Double> averageWarehousesPerUser = new LinkedHashMap<>();
    if (!userRepository.findAll().isEmpty() && !statsRepository.findAll().isEmpty()) {

      Double usersCount = (double) userRepository.findAll().stream()
          .filter(user -> user.getRegisteredAt().isAfter(LocalDateTime.now().minusMonths(6))).count();
      Map<String, Integer> warehousesCreatedPerEveryMonth = statsRepository.findAll().get(0).getWarehousesCreatedPerEveryMonth();
      warehousesCreatedPerEveryMonth.keySet().forEach(month -> {
        averageWarehousesPerUser.put(month, usersCount / warehousesCreatedPerEveryMonth.get(month));
      });
      saveOrUpdateAverageWarehousesPerUser(averageWarehousesPerUser);
    }
  }

  @Scheduled(fixedDelay = 5000)
  public void generateAverageProductsPerWarehouse(){
    LinkedHashMap<String, Double> averageProductsPerWarehouse = new LinkedHashMap<>();
    if (!statsRepository.findAll().isEmpty()) {
      Stats stats = statsRepository.findAll().get(0);
      Map<String, Integer> warehousesCreatedPerEveryMonth = stats.getWarehousesCreatedPerEveryMonth();
      Map<String, Integer> productsCreatedPerEveryMonth = stats.getProductsCreatedPerEveryMonth();
      warehousesCreatedPerEveryMonth.keySet().forEach(month -> {
        if(productsCreatedPerEveryMonth.containsKey(month)){
          averageProductsPerWarehouse.put(month, Double.valueOf(productsCreatedPerEveryMonth.get(month)) / Double.valueOf(warehousesCreatedPerEveryMonth.get(month)));
        }
      });
      saveOrUpdateAverageProductsPerWarehouse(averageProductsPerWarehouse);
    }
  }

  public void saveOrUpdateAverageProductsPerWarehouse(Map<String, Double> averageProductsPerWarehouse){
    Stats stats = new Stats();
    stats.setAverageProductsPerWarehouse(averageProductsPerWarehouse);
    List<Stats> allStats = statsRepository.findAll();
    if (allStats.isEmpty()) {
      statsRepository.save(stats);
    } else {
      Stats statsToUpdate = allStats.get(0);
      statsToUpdate.setAverageProductsPerWarehouse(averageProductsPerWarehouse);
      statsRepository.save(statsToUpdate);
    }
  }

  public void saveOrUpdateAverageWarehousesPerUser(Map<String, Double> averageWarehousesPerUser){
    Stats stats = new Stats();
    stats.setAverageWarehousesPerUser(averageWarehousesPerUser);
    List<Stats> allStats = statsRepository.findAll();
    if (allStats.isEmpty()) {
      statsRepository.save(stats);
    } else {
      Stats statsToUpdate = allStats.get(0);
      statsToUpdate.setAverageWarehousesPerUser(averageWarehousesPerUser);
      statsRepository.save(statsToUpdate);
    }
  }

  @Scheduled(fixedDelay = 5000)
  public void generateWarehousesPerUser() {
    LinkedHashMap<String, Integer> warehousesPerUser = new LinkedHashMap<>();
    if (!userRepository.findAll().isEmpty()) {
      userRepository.findAll().forEach(user -> {
        int usersWarehousesCount = user.getWarehouses().size();
        switch (usersWarehousesCount) {
          case 0:
            break;
          case 1:
            if (warehousesPerUser.containsKey("ONE")) {
              warehousesPerUser.put("ONE", warehousesPerUser.get("ONE") + 1);
            } else {
              warehousesPerUser.put("ONE", 1);
            }
          case 2:
            if (warehousesPerUser.containsKey("TWO")) {
              warehousesPerUser.put("TWO", warehousesPerUser.get("TWO") + 1);
            } else {
              warehousesPerUser.put("TWO", 1);
            }
          case 3:
            if (warehousesPerUser.containsKey("THREE")) {
              warehousesPerUser.put("THREE", warehousesPerUser.get("THREE") + 1);
            } else {
              warehousesPerUser.put("THREE", 1);
            }
          default:
            if (warehousesPerUser.containsKey("FOUR OR MORE")) {
              warehousesPerUser.put("FOUR OR MORE", warehousesPerUser.get("FOUR OR MORE") + 1);
            } else {
              warehousesPerUser.put("FOUR OR MORE", 1);
            }
        }
      });
    }
    saveOrUpdateWarehousesPerUser(warehousesPerUser);
  }

  private void saveOrUpdateWarehousesPerUser(Map<String, Integer> warehousesPerUser) {
    Stats stats = new Stats();
    stats.setWarehousesPerUser(warehousesPerUser);
    List<Stats> allStats = statsRepository.findAll();
    if (allStats.isEmpty()) {
      statsRepository.save(stats);
    } else {
      Stats statsToUpdate = allStats.get(0);
      statsToUpdate.setWarehousesPerUser(warehousesPerUser);
      statsRepository.save(statsToUpdate);
    }
  }

  private void saveOrUpdateUsersRegisteredPerEveryMonth(Map<String, Integer> usersRegisteredPerEveryMonth) {
    Stats stats = new Stats();
    stats.setUsersRegisteredPerEveryMonth(usersRegisteredPerEveryMonth);
    List<Stats> allStats = statsRepository.findAll();
    if (allStats.isEmpty()) {
      statsRepository.save(stats);
    } else {
      Stats statsToUpdate = allStats.get(0);
      statsToUpdate.setUsersRegisteredPerEveryMonth(usersRegisteredPerEveryMonth);
      statsRepository.save(statsToUpdate);
    }
  }

  private void saveOrUpdateProductsPerEveryMonth(Map<String, Integer> productsCreatedPerEveryMonth) {
    Stats stats = new Stats();
    stats.setProductsCreatedPerEveryMonth(productsCreatedPerEveryMonth);
    List<Stats> allStats = statsRepository.findAll();
    if (allStats.isEmpty()) {
      statsRepository.save(stats);
    } else {
      Stats statsToUpdate = allStats.get(0);
      statsToUpdate.setProductsCreatedPerEveryMonth(productsCreatedPerEveryMonth);
      statsRepository.save(statsToUpdate);
    }
  }

  private void saveOrUpdateWarehousesPerEveryMonth(Map<String, Integer> warehousesCreatedPerEveryMonth) {
    Stats stats = new Stats();
    stats.setWarehousesCreatedPerEveryMonth(warehousesCreatedPerEveryMonth);
    List<Stats> allStats = statsRepository.findAll();
    if (allStats.isEmpty()) {
      statsRepository.save(stats);
    } else {
      Stats statsToUpdate = allStats.get(0);
      statsToUpdate.setWarehousesCreatedPerEveryMonth(warehousesCreatedPerEveryMonth);
      statsRepository.save(statsToUpdate);
    }
  }

}
