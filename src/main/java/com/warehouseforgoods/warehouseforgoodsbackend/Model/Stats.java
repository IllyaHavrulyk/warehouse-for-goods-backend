package com.warehouseforgoods.warehouseforgoodsbackend.Model;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Stats")
public class Stats {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ElementCollection(fetch = FetchType.EAGER)
  @MapKeyColumn(name = "month_warehouses")
  @Column(name = "warehouses_per_month")
  private Map<String, Integer> warehousesCreatedPerEveryMonth;


  @ElementCollection(fetch = FetchType.EAGER)
  @MapKeyColumn(name = "month_products")
  @Column(name = "products_per_month")
  private Map<String, Integer> productsCreatedPerEveryMonth;

  @ElementCollection(fetch = FetchType.EAGER)
  @MapKeyColumn(name = "month_users")
  @Column(name = "users_per_month")
  private Map<String, Integer> usersRegisteredPerEveryMonth;

  @ElementCollection(fetch = FetchType.EAGER)
  @MapKeyColumn(name = "warehouses_count")
  @Column(name = "users_warehouses")
  private Map<String, Integer> warehousesPerUser;

  @ElementCollection(fetch = FetchType.EAGER)
  @MapKeyColumn(name = "month_average_warehouses")
  @Column(name = "average_warehouses_per_user")
  private Map<String, Double> averageWarehousesPerUser;

  @ElementCollection(fetch = FetchType.EAGER)
  @MapKeyColumn(name = "month_average_products")
  @Column(name = "average_products_per_warehouse")
  private Map<String, Double> averageProductsPerWarehouse;

  public Map<String, Double> getAverageProductsPerWarehouse() {
    return averageProductsPerWarehouse;
  }

  public void setAverageProductsPerWarehouse(Map<String, Double> averageProductsPerWarehouse) {
    this.averageProductsPerWarehouse = averageProductsPerWarehouse;
  }

  public Map<String, Double> getAverageWarehousesPerUser() {
    return averageWarehousesPerUser;
  }

  public void setAverageWarehousesPerUser(Map<String, Double> averageWarehousesPerUser) {
    this.averageWarehousesPerUser = averageWarehousesPerUser;
  }

  public Map<String, Integer> getWarehousesPerUser() {
    return warehousesPerUser;
  }

  public void setWarehousesPerUser(Map<String, Integer> warehousesPerUser) {
    this.warehousesPerUser = warehousesPerUser;
  }

  public Map<String, Integer> getUsersRegisteredPerEveryMonth() {
    return usersRegisteredPerEveryMonth;
  }

  public void setUsersRegisteredPerEveryMonth(Map<String, Integer> usersRegisteredPerEveryMonth) {
    this.usersRegisteredPerEveryMonth = usersRegisteredPerEveryMonth;
  }

  public Map<String, Integer> getProductsCreatedPerEveryMonth() {
    return productsCreatedPerEveryMonth;
  }

  public void setProductsCreatedPerEveryMonth(Map<String, Integer> productsCreatedPerEveryMonth) {
    this.productsCreatedPerEveryMonth = productsCreatedPerEveryMonth;
  }

  public Map<String, Integer> getWarehousesCreatedPerEveryMonth() {
    return warehousesCreatedPerEveryMonth;
  }

  public void setWarehousesCreatedPerEveryMonth(Map<String, Integer> warehousesCreatedPerEveryMonth) {
    this.warehousesCreatedPerEveryMonth = warehousesCreatedPerEveryMonth;
  }
}
