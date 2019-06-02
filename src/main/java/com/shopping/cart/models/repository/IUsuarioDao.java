package com.shopping.cart.models.repository;

import com.shopping.cart.models.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface IUsuarioDao extends CrudRepository<User, Long> {

    public User findByUsername(String username);

    @Query("select u from User u where u.username=?1")
    public User findByUsername2(String username);

    public boolean existsByEmail(String email);

    public boolean existsByUsername(String username);

    public boolean existsByUsernameOrEmail(String username, String email);
}
