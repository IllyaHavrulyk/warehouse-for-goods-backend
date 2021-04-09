package com.warehouseforgoods.warehouseforgoodsbackend.Service;

import com.warehouseforgoods.warehouseforgoodsbackend.Error.UserExceptions;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.User;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.Warehouse;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.UserRepository;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.WarehouseRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Warehouse getById(Long id) {
        return warehouseRepository.findById(id).orElseThrow(
                () -> new UserExceptions(UserExceptions.Error.USER_DAO_GET_FAILED)
        );
    }

    @Override
    public void save(Warehouse warehouse, Principal principal) {
        User currentUser = getCurrentUser(principal);
        warehouse.setDateAdded(LocalDateTime.now());
        currentUser.addWarehouse(warehouse);
        warehouseRepository.save(warehouse);
        userRepository.save(currentUser);
    }

    @Override
    public void delete(Long id,Principal principal) {
        User currentUser = getCurrentUser(principal);
        Warehouse warehouse = getById(id);
        currentUser.removeProduct(warehouse);
        warehouseRepository.deleteById(id);
    }

    @Override
    public List<Warehouse> getAll(Principal principal) {
        User currentUser = getCurrentUser(principal);
        return currentUser.getWarehouses();
    }

    private User getCurrentUser(Principal principal) {
        return userRepository.findByUsername(principal.getName()).orElseThrow(
                () -> new UserExceptions(UserExceptions.Error.USER_DAO_GET_BY_EMAIL_FAILED)
        );
    }
}
