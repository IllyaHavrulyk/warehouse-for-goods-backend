package com.warehouseforgoods.warehouseforgoodsbackend.Service;

import com.warehouseforgoods.warehouseforgoodsbackend.Error.ProductExceptions;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.Product;
import com.warehouseforgoods.warehouseforgoodsbackend.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductExceptions(ProductExceptions.Error.PRODUCT_DAO_GET_FAILED));
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> search(String searchValue) {
        List<Product> products = productRepository.findAll();
        List<Product> foundProducts = new ArrayList<>();

        for (Product product : products) {
            if (product.getDescription() != null) {
                if (product.getName().contains(searchValue) || product.getDescription().contains(searchValue)) {
                    foundProducts.add(product);
                }
            } else {
                if (product.getName().contains(searchValue)) {
                    foundProducts.add(product);
                }
            }
        }

        return foundProducts;
    }

    @Override
    public List<Product> filter(BigDecimal minPrice, BigDecimal maxPrice) {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .filter(product -> (product.getPrice().compareTo(minPrice) >= 0)
                        && (product.getPrice().compareTo(maxPrice) <= 0))
                .collect(Collectors.toList());
    }
}
