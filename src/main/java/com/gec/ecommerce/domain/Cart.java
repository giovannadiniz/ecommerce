package com.gec.ecommerce.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Setter
@Getter
@Entity
@Table(name = "carts", schema = "trade")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 1;  // Valor padrão 1

    @Column(name = "total", precision = 19, scale = 2, nullable = false)
    private BigDecimal total;

    // Método para calcular o total baseado no preço do produto
    public void calculateTotal() {
        if (product != null && product.getPrice() != null) {
            this.total = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        } else {
            this.total = BigDecimal.ZERO;
        }
    }

    public Cart() {
    }

}