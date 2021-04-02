package com.warehouseforgoods.warehouseforgoodsbackend.Controller;

import com.warehouseforgoods.warehouseforgoodsbackend.Model.Warehouse;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.UserRepository;
import com.warehouseforgoods.warehouseforgoodsbackend.Service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping(value="/save",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Warehouse> saveWarehouse(
            @RequestBody Warehouse warehouse,
            Principal principal){
        HttpHeaders httpHeaders = new HttpHeaders();
        warehouseService.save(warehouse,principal);

        return new ResponseEntity<>(warehouse,httpHeaders,HttpStatus.CREATED);
    }


    @DeleteMapping(value = "/delete/{warehouseId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Warehouse> deleteProduct
            (@PathVariable("warehouseId") Long warehouseId,
             Principal principal){

        warehouseService.delete(warehouseId,principal);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value="/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Warehouse>> getAllProducts(Principal principal){

        List<Warehouse> warehouses = warehouseService.getAll(principal);

        return new ResponseEntity<>(warehouses,HttpStatus.OK);
    }
}
