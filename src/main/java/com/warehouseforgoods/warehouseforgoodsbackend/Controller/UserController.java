package com.warehouseforgoods.warehouseforgoodsbackend.Controller;

import com.warehouseforgoods.warehouseforgoodsbackend.Model.Role;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.Status;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.User;
import com.warehouseforgoods.warehouseforgoodsbackend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;


    @GetMapping("/user/list")
    public List<User> getUsers(){
        return userService.getAll();
    }
    @PostMapping("/registration")
    public ResponseEntity<User> registration(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
        userService.save(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

}
