package com.tc.stockcontrol.product;

import com.tc.stockcontrol.product.enums.Category;
import com.tc.stockcontrol.product.enums.CategoryConverter;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "price", nullable = false)
    Double price;

    @Column(name = "category", nullable = false)
    @Convert(converter = CategoryConverter.class)
    Category category;

    @Column(name = "quantity", nullable = false)
    Integer quantity;

    @Column(name = "expiration_date", nullable = false)
    Date expirationDate;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    Date createdAt;

    @Column(name = "bar_code", nullable = false, unique = true)
    String barCode;
}
