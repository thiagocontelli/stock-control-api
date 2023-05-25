package com.tc.stockcontrol.dtos.product;

import java.util.Date;

public record ProductReqDTO(
        String name,
        Double price,
        Integer category,
        Integer quantity,
        String barCode,
        Date expirationDate
) {
}
