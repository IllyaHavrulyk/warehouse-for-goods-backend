package com.warehouseforgoods.warehouseforgoodsbackend.Service;

import com.warehouseforgoods.warehouseforgoodsbackend.Error.UserAlreadyExistException;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.Role;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.Status;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.User;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void save(User user) {
        if(userRepository.findByUsername(user.getUsername()).isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Role.USER);
            user.setStatus(Status.ACTIVE);
            userRepository.save(user);
        }
        else throw new UserAlreadyExistException("User with this username already exist");
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

}
