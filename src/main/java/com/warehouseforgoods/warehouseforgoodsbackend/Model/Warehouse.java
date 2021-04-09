package com.warehouseforgoods.warehouseforgoodsbackend.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDateTime;
import javax.persistence.*;
import java.util.List;

@Entity
public class Warehouse {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @JsonBackReference
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "warehouse")
    private List<Product> products;

    private LocalDateTime dateAdded;

    public Warehouse() {
    }

    public void addProduct(Product product){
        products.add(product);
        product.setWarehouse(this);
    }
    public void removeProduct(Product product){
        products.remove(product);
        product.setWarehouse(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    private void setProducts(List<Product> products) {
        this.products = products;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }
}
