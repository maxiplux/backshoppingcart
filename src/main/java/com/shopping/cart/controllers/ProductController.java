package com.shopping.cart.controllers;

import com.shopping.cart.errors.ValidationErrorBuilder;
import com.shopping.cart.models.entity.Product;
import com.shopping.cart.services.ProductService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
//https://phoenixnap.com/kb/spring-boot-validation-for-rest-services


    @Autowired
    private ProductService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> queryByPage(Pageable pageable) {
        //https://dzone.com/articles/conditional-pagination-and-sorting-using-restful-w
        Page<Product> pageInfo = service.findAll(pageable);
        if (pageInfo.getContent().isEmpty()) {
            return new ResponseEntity<Page<Product>>(pageInfo, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Page<Product>>(pageInfo, HttpStatus.OK);

    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization ", required = true, dataType = "string", paramType = "header", defaultValue = "Bearer ")
    })
    @GetMapping(value = "{id}/")
    public ResponseEntity<?> findById(@PathVariable long id) {

        Optional<Product> productOptional = service.findById(id);
        if (productOptional.isPresent()) {
            return new ResponseEntity<Product>(productOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Product>(productOptional.get(), HttpStatus.NOT_FOUND);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization ", required = true, dataType = "string", paramType = "header", defaultValue = "Bearer ")
    })
    @PostMapping
    public ResponseEntity<?> creatProduct(@Valid @RequestBody Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }

        return new ResponseEntity<Product>(service.createProduct(product), HttpStatus.OK);

    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization ", required = true, dataType = "string", paramType = "header", defaultValue = "Bearer ")
    })
    @PutMapping("{id}/")
    public ResponseEntity<?> updateEmployee(@PathVariable("id") long id, @Valid @RequestBody Product productForUpdate, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }

        Optional<Product> optionalProduct = this.service.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(productForUpdate.getName());
            product.setPrice(productForUpdate.getPrice());
            return new ResponseEntity<Product>(service.updateProduct(product), HttpStatus.OK);
        }
        return new ResponseEntity<String>(new String("No Data found"), HttpStatus.NOT_FOUND);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization ", required = true, dataType = "string", paramType = "header", defaultValue = "Bearer ")
    })
    @PatchMapping("{id}/")
    public ResponseEntity<?> updateEmployeeByUser(@RequestParam("id") long id, @Valid @RequestBody Product product, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }

        Optional<Product> optionalProduct = service.updateProductById(id, product);
        if (optionalProduct.isPresent()) {
            return new ResponseEntity<Product>(optionalProduct.get(), HttpStatus.OK);
        }
        return new ResponseEntity<String>(new String("Without updates"), HttpStatus.BAD_REQUEST);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization ", required = true, dataType = "string", paramType = "header", defaultValue = "Bearer ")
    })
    @DeleteMapping(value = "{id}/")
    public void deleteEmployeeById(@PathVariable long id) {
        service.deleteProductById(id);
    }


}
