package com.shopping.cart.services;

import com.shopping.cart.models.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {
    Page<Product> findAll(Pageable pageable);

    Product createProduct(Product product);

    Product updateProduct(Product product);

    Optional<Product> updateProductById(long id, Product product);

    Boolean deleteProductById(long id);

    Optional<Product> findById(long id);
}
