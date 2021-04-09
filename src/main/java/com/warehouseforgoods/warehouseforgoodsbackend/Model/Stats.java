package com.warehouseforgoods.warehouseforgoodsbackend.Model;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
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

  @ElementCollection
  @MapKeyColumn(name = "month")
  @Column(name = "warehouses")
  private Map<String, Integer> warehousesCreatedPerEveryMonth;


  public Map<String, Integer> getWarehousesCreatedPerEveryMonth() {
    return warehousesCreatedPerEveryMonth;
  }

  public void setWarehousesCreatedPerEveryMonth(Map<String, Integer> warehousesCreatedPerEveryMonth) {
    this.warehousesCreatedPerEveryMonth = warehousesCreatedPerEveryMonth;
  }
}
