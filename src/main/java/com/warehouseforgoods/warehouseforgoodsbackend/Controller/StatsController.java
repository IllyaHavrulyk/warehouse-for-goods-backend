package com.warehouseforgoods.warehouseforgoodsbackend.Controller;

import com.warehouseforgoods.warehouseforgoodsbackend.Model.Product;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.Stats;
import com.warehouseforgoods.warehouseforgoodsbackend.Service.StatsService;
import java.net.http.HttpResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatsController {

  @Autowired
  private StatsService statsService;

  @GetMapping(value = "/list",  produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Stats> statsList(){
    return new ResponseEntity<>(statsService.getAll().get(0), HttpStatus.OK);
  }
}
