package com.shopping.cart.controllers;


import com.shopping.cart.errors.ValidationErrorBuilder;
import com.shopping.cart.models.entity.User;
import com.shopping.cart.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth/")
public class AuthController {
    @Autowired
    private UserService service;


    @PostMapping("/singin")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, Errors errors) {
        if (this.service.existsByUsername(user.getUsername())) {

            return ResponseEntity.badRequest().body("Error User Invalid");
        }

        if (this.service.existsByEmail(user.getEmail())) {

            return ResponseEntity.badRequest().body("Error email Invalid");
        }

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }


        return new ResponseEntity<User>(service.save(user), HttpStatus.OK);

    }
}
