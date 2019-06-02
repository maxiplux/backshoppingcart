package com.shopping.cart.models.repository;

import com.shopping.cart.models.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    @Query("select p from Product p where p.name like %?1%")
    public List<Product> findByName(String term);

    public List<Product> findByNameContainingIgnoreCase(String term);

    public Boolean deleteById(long id);

    public List<Product> findByNameStartingWithIgnoreCase(String term);
}
