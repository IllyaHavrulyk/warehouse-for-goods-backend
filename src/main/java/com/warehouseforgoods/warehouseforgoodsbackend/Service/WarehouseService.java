package com.warehouseforgoods.warehouseforgoodsbackend.Service;

import com.warehouseforgoods.warehouseforgoodsbackend.Model.Warehouse;

import java.security.Principal;
import java.util.List;

public interface WarehouseService {
    Warehouse getById(Long id);

    void save(Warehouse warehouse, Principal principal);

    void delete(Long id,Principal principal);

    List<Warehouse> getAll(Principal principal);
}
