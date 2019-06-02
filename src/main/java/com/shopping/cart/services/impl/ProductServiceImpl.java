package com.shopping.cart.services.impl;

import com.shopping.cart.models.entity.Product;
import com.shopping.cart.models.repository.ProductRepository;
import com.shopping.cart.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return this.productRepository.save(product);
    }


    @Override
    public Optional<Product> updateProductById(long id, Product product) {
        Optional<Product> optionalCurrentProduct = this.productRepository.findById(id);
        if (optionalCurrentProduct.isPresent()) {
            Product currentProduct = optionalCurrentProduct.get();

            if (product.getName() != null) {
                currentProduct.setName(product.getName());
            }

            if (product.getPrice() != null) {
                currentProduct.setPrice(product.getPrice());
            }

            return Optional.of(this.productRepository.save(currentProduct));
        }
        return Optional.empty();


    }


    @Override
    public Boolean deleteProductById(long id) {
        return this.productRepository.deleteById(id);
    }

    @Override
    public Optional<Product> findById(long id) {
        return this.productRepository.findById(id);
    }
}
