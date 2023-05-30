package com.tc.stockcontrol.product.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.Date;

public record ProductResDTO(
        @NotBlank
        @NotEmpty
        String id,

        @NotBlank
        @NotEmpty
        String name,

        @Positive
        Double price,

        String category,

        @Positive
        Integer quantity,

        @NotBlank
        @NotEmpty
        String barCode,

        Date expirationDate,

        Date createdAt
) {
}
