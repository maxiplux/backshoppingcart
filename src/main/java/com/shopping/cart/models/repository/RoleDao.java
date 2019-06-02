package com.shopping.cart.models.repository;

import com.shopping.cart.models.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface RoleDao extends CrudRepository<Role, Long> {

    public List<Role> findAll();
}