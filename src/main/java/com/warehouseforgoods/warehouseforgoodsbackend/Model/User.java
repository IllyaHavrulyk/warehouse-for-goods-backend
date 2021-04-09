package com.warehouseforgoods.warehouseforgoodsbackend.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull(message = "Provide a username")
    private String username;
    @NotNull(message = "Provide a password")
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Warehouse> warehouses = new ArrayList<>();



    public User(){
    }

    public void addWarehouse(Warehouse warehouse){
        warehouses.add(warehouse);
        warehouse.setUser(this);
    }
    public void removeProduct(Warehouse warehouse){
        warehouses.remove(warehouse);
        warehouse.setUser(null);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    private void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }
}
