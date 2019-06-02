package com.shopping.cart.products;

import com.shopping.cart.models.entity.Product;
import com.shopping.cart.models.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testPage1() {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<Product> students = productRepository.findAll(pageable);
        assertEquals(students.getSize(), pageSize);
    }

    @Test
    public void testPageAndSort() {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(0, pageSize, Sort.Direction.ASC, "id"); //order by id asc
        Page<Product> products = productRepository.findAll(pageable);
        assertEquals(products.getSize(), pageSize);
        assertFalse(products.getContent().isEmpty());
    }
}
