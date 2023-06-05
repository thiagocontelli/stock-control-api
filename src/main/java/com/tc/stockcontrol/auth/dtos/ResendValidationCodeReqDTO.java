package com.tc.stockcontrol.auth.dtos;

import jakarta.validation.constraints.Email;

public record ResendValidationCodeReqDTO(@Email String email) {
}