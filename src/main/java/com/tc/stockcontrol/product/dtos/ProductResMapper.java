package com.tc.stockcontrol.product.dtos;

import com.tc.stockcontrol.product.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductResMapper {
    public ProductResDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductResDTO(product.getId(), product.getName(), product.getPrice(), product.getCategory().getValue(), product.getQuantity(), product.getBarCode(), product.getExpirationDate(), product.getCreatedAt());
    }
}
