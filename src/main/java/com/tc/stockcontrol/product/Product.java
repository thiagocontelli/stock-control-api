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
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "category", nullable = false)
    @Convert(converter = CategoryConverter.class)
    private Category category;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Column(name = "bar_code", nullable = false, unique = true)
    private String barCode;
}
