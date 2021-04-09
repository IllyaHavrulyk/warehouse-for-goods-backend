package com.warehouseforgoods.warehouseforgoodsbackend.Service;

import com.warehouseforgoods.warehouseforgoodsbackend.Error.AuthorityException;
import com.warehouseforgoods.warehouseforgoodsbackend.Error.ProductExceptions;
import com.warehouseforgoods.warehouseforgoodsbackend.Error.UserExceptions;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.Product;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.User;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.Warehouse;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.ProductRepository;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.UserRepository;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    public Product getById(Long productId) {
        Product product= productRepository.findById(productId)
                .orElseThrow(() -> new ProductExceptions(ProductExceptions.Error.PRODUCT_DAO_GET_FAILED));
        checkAuthorities(product);
        return product;
    }
    private void checkAuthorities(Product product){
        if(!isProductBelongToPrincipal(product)){
            throw new AuthorityException("You don't have authorities for this");
        }
    }

    private boolean isProductBelongToPrincipal(Product product) {
        User currentUser = getCurrentUser();
        return currentUser.getWarehouses().contains(product.getWarehouse());
    }

    private User getCurrentUser(){
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername()).orElseThrow(
                () -> new UserExceptions(UserExceptions.Error.USER_DAO_GET_BY_EMAIL_FAILED)
        );
    }

    @Override
    public void save(Product product, Long warehouseId) {
        product.setDateAdded(LocalDateTime.now());
        Warehouse warehouse = warehouseService.getById(warehouseId);
        checkAuthorities(product);
        warehouse.addProduct(product);
        productRepository.save(product);
        warehouseRepository.save(warehouse);
    }

    @Override
    public void delete(Long productId,Long warehouseId) {
        Warehouse warehouse = warehouseService.getById(warehouseId);
        Product product = getById(productId);
        checkAuthorities(product);
        warehouse.removeProduct(product);
        productRepository.delete(product);
    }

    @Override
    public List<Product> getAll(Long warehouseId) {
        return warehouseService.getById(warehouseId).getProducts();
    }

    @Override
    public List<Product> search(String searchValue,Long warehouseId) {
        List<Product> products = getAll(warehouseId);
        String finalSearchValue = searchValue.toLowerCase();

        return products.stream()
                .filter(product -> containsInName(finalSearchValue,product)
                        || containsInDescription(finalSearchValue,product))
                .collect(Collectors.toList());
    }

    private boolean containsInDescription(String searchValue, Product product){
        if(product.getDescription()==null){
            return false;
        }
        return product.getDescription().toLowerCase().contains(searchValue);
    }

    private boolean containsInName(String searchValue, Product product){
        if(product.getName()==null){
            return false;
        }
        return product.getName().toLowerCase().contains(searchValue);
    }

    @Override
    public List<Product> filter(BigDecimal minPrice, BigDecimal maxPrice,Long warehouseId) {
        List<Product> products = getAll(warehouseId);

        return products.stream()
                .filter(product -> (product.getPrice().compareTo(minPrice) >= 0)
                        && (product.getPrice().compareTo(maxPrice) <= 0))
                .collect(Collectors.toList());
    }
}
