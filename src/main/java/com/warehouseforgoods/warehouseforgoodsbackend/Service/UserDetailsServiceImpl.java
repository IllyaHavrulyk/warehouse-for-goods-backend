package com.warehouseforgoods.warehouseforgoodsbackend.Service;

import com.warehouseforgoods.warehouseforgoodsbackend.Model.User;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.UserRepository;
import com.warehouseforgoods.warehouseforgoodsbackend.Security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username).orElseThrow(()->
                new UsernameNotFoundException("User doesn't exist"));
        return SecurityUser.fromUser(user);
    }
}
