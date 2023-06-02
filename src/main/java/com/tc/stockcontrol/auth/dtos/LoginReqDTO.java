package com.tc.stockcontrol.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record LoginReqDTO(
        @NotBlank
        @NotEmpty
        @Email
        String email,

        @NotBlank
        @NotEmpty
        String password
) {
}
