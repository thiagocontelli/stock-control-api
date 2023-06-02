package com.tc.stockcontrol.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record SignUpReqDTO(
        @NotBlank
        @NotEmpty
        String name,

        @NotBlank
        @NotEmpty
        @Email
        String email,

        @NotBlank
        @NotEmpty
        String password) {
}
