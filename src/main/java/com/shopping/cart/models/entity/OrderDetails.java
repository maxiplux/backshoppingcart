package com.shopping.cart.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class OrderDetails extends AuditModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private PurchaceOrder purchaceOrder;


    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    @NotEmpty(message = "no puede estar vacio")
    @Column(nullable = false)
    private Float quantity;

    public OrderDetails() {
    }

    public OrderDetails(@NotEmpty(message = "no puede estar vacio") Product product, @NotEmpty(message = "no puede estar vacio") Float quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PurchaceOrder getPurchaceOrder() {
        return purchaceOrder;
    }

    public void setPurchaceOrder(PurchaceOrder purchaceOrder) {
        this.purchaceOrder = purchaceOrder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }
}
