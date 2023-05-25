package com.tc.stockcontrol.dtos.product;

import com.tc.stockcontrol.product.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductReqMapper {
    public ProductReqDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductReqDTO(product.getName(), product.getPrice(), product.getCategory(), product.getQuantity(), product.getBarCode(), product.getExpirationDate());
    }

    public Product toEntity(ProductReqDTO dto) {
        if (dto == null) {
            return null;
        }
        Product product = new Product();
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setCategory(dto.category());
        product.setQuantity(dto.quantity());
        product.setExpirationDate(dto.expirationDate());
        product.setBarCode(dto.barCode());
        return product;
    }
}
