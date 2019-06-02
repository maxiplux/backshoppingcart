package com.shopping.cart.services.impl;

import com.shopping.cart.models.entity.Role;
import com.shopping.cart.models.entity.User;
import com.shopping.cart.models.repository.IUsuarioDao;
import com.shopping.cart.models.repository.RoleDao;
import com.shopping.cart.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService, UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private IUsuarioDao usuarioDao;

    @Autowired
    private RoleDao roleRepository;


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = usuarioDao.findByUsername(username);

        if (user == null) {
            logger.error("Error en el login: no existe el user '" + username + "' en el sistema!");
            throw new UsernameNotFoundException("Error en el login: no existe el user '" + username + "' en el sistema!");
        }

        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .peek(authority -> logger.info("Role: " + authority.getAuthority()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return usuarioDao.findByUsername(username);
    }

    @Override

    public User save(User user) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        List<Role> roles = this.roleRepository.findAll().stream().filter(role -> user.getRoles().contains(role)).collect(Collectors.toList());
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword().toString()));
        user.setUsername(user.getUsername().toLowerCase());
        return this.usuarioDao.save(user);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return this.usuarioDao.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String username) {
        return this.usuarioDao.existsByEmail(username);
    }

}
