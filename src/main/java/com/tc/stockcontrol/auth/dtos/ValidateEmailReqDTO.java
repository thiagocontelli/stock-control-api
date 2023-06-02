package com.tc.stockcontrol.auth.dtos;

import jakarta.validation.constraints.Email;

public record ValidateEmailReqDTO(@Email String email, int code) {
}
