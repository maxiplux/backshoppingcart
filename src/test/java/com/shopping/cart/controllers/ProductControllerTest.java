package com.shopping.cart.controllers;

import com.shopping.cart.CartApplication;
import utils.PageSerializer;
import utils.TokenSerializer;
import com.shopping.cart.models.entity.Product;
import org.apache.commons.codec.binary.Base64;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;

import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CartApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductControllerTest {


    //All tests here are integration tests
    private final String BASE_URL = "/api/v1/products";


    @Autowired
    TestRestTemplate restTemplate;

    HttpEntity<Product> entity;

    @Value("${security.passwordencoder}")

    private String passwordencoder;

    @Value("${security.withclient}")
    private String withclient;
    Product product;
    private String token;

    private void setHeaders(Product product) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + this.token);
        entity = new HttpEntity<>(product, headers);
    }


    private void login() {

        String plainCreds = String.format("%s:%s", this.withclient, this.passwordencoder);
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds.toString());
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();

        body.add("username", "admin");
        body.add("password", "12345");
        body.add("grant_type", "password");

        HttpEntity<?> request = new HttpEntity<Object>(body, headers);


        ResponseEntity<TokenSerializer> response = restTemplate.exchange("/oauth/token", HttpMethod.POST, request, TokenSerializer.class);
        Assert.notNull(response.getBody().getAccess_token());

        this.token = response.getBody().getAccess_token();


//        ResponseEntity<JsonNode> response = restTemplate.exchange("/oauth/token",HttpMethod.GET,user, JsonNode.class);

//        this.token = response.getBody().get("access_token").textValue();
    }


    @Before
    public void setUp() throws Exception {
       this.login();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void queryByPage() {
        ResponseEntity<PageSerializer> result = restTemplate.exchange("/api/v1/products/", HttpMethod.GET, entity, PageSerializer.class);
        assertNotNull(result.getBody().getContent());
    }


    @Test
    public void findById() {
    }

    @Test
    public void creatProduct() {
    }

    @Test
    public void updateEmployee() {
    }

    @Test
    public void updateEmployeeByUser() {
    }

    @Test
    public void deleteEmployeeById() {
    }
}