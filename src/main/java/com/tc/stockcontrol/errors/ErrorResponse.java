package com.tc.stockcontrol.errors;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ErrorResponse {
    private String message;
}
