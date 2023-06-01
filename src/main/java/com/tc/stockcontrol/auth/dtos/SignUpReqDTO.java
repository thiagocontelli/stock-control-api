package com.tc.stockcontrol.auth.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record SignUpReqDTO(
        @NotBlank
        @NotEmpty
        String name,

        @NotBlank
        @NotEmpty
        String login,

        @NotBlank
        @NotEmpty
        String password) {
}
