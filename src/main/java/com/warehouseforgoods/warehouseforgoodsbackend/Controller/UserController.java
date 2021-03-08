package com.warehouseforgoods.warehouseforgoodsbackend.Controller;

import com.warehouseforgoods.warehouseforgoodsbackend.Model.User;
import com.warehouseforgoods.warehouseforgoodsbackend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;


    @GetMapping("/user/list")
    public List<User> getUsers(){
        return userService.getAll();
    }

    @PostMapping("/registration")
    public ResponseEntity<User> registration(@Valid @RequestBody User user){
        userService.save(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

}
