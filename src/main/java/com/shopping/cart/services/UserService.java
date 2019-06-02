package com.shopping.cart.services;

import com.shopping.cart.models.entity.User;

public interface UserService {

    public User findByUsername(String username);

    public User save(User user);

    public Boolean existsByUsername(String username);

    boolean existsByEmail(String username);
}
