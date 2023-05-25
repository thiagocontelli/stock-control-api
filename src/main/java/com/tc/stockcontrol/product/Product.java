package com.tc.stockcontrol.product;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Product {
    @Id
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    Long id;
    String name;
    Double price;
    Integer category;
    Integer quantity;
    Long expirationDate;
    Long createdAt;
}
