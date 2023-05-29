package com.tc.stockcontrol.dtos.product;

import com.tc.stockcontrol.errors.BadRequestException;
import com.tc.stockcontrol.product.enums.Category;
import com.tc.stockcontrol.product.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductReqMapper {
    public ProductReqDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductReqDTO(product.getName(), product.getPrice(), product.getCategory().getValue(), product.getQuantity(), product.getBarCode(), product.getExpirationDate());
    }

    public Product toEntity(ProductReqDTO dto) {
        if (dto == null) {
            return null;
        }
        Product product = new Product();
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setCategory(convertValueToCategory(dto.category()));
        product.setQuantity(dto.quantity());
        product.setExpirationDate(dto.expirationDate());
        product.setBarCode(dto.barCode());
        return product;
    }

    public Category convertValueToCategory(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Groceries" -> Category.GROCERIES;
            case "Beverages" -> Category.BEVERAGES;
            case "Household-Items" -> Category.HOUSEHOLD_ITEMS;
            default -> throw new BadRequestException("invalid_category");
        };
    }
}
