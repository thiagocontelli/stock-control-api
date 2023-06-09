package com.tc.stockcontrol.product.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.Date;

public record ProductReqDTO(
        @NotEmpty
        @NotBlank
        String name,

        @Positive
        Double price,

        String category,

        @Positive
        Integer quantity,

        @NotEmpty
        @NotBlank
        String barCode,

        @Future
        Date expirationDate
) {
}
