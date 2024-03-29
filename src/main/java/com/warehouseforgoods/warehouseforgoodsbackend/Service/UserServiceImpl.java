package com.warehouseforgoods.warehouseforgoodsbackend.Service;

import com.warehouseforgoods.warehouseforgoodsbackend.Error.UserExceptions;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.Role;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.Status;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.User;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.UserRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserExceptions(UserExceptions.Error.USER_DAO_GET_FAILED)
        );
    }

    @Override
    public void save(User user) {
        if(userRepository.findByUsername(user.getUsername()).isEmpty()) {
            user.setRegisteredAt(LocalDateTime.now());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Role.USER);
            user.setStatus(Status.ACTIVE);
            userRepository.save(user);
        }
        else throw new UserExceptions(UserExceptions.Error.USER_DAO_GET_BY_EMAIL_FAILED);
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
