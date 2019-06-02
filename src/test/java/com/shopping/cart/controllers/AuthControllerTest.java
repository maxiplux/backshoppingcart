package com.shopping.cart.controllers;

import com.shopping.cart.CartApplication;
import com.shopping.cart.models.entity.Product;
import org.apache.commons.codec.binary.Base64;
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
import utils.TokenSerializer;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CartApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    HttpEntity<Product> entity;
    String token = "";

    @Value("${security.passwordencoder}")

    private String passwordencoder;

    @Value("${security.withclient}")
    private String withclient;


    @Test
    public void doLoginTest() {


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
        //   this.token = response.getBody().getAccess_token();
    }


    @Test
    public void tokenForRefreshTest() {
        String plainCreds = String.format("%s:%s", this.withclient, this.passwordencoder);
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds.toString());
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();

        body.add("refresh_token", this.getTokenForRefresh());

        body.add("grant_type", "refresh_token");
        HttpEntity<?> request = new HttpEntity<Object>(body, headers);
        ResponseEntity<TokenSerializer> response = restTemplate.exchange("/oauth/token", HttpMethod.POST, request, TokenSerializer.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);


    }


    public String getTokenForRefresh() {


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

        return response.getBody().getRefresh_token();
    }

}
