package com.example.auth.model

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class LoginRequest(
    @field:Email(message = "Email should be valid")
    @field:NotBlank(message = "Email is mandatory")
    val email: String,

    @field:Size(min = 8, message = "Password should be at least 8 characters long")
    @field:NotBlank(message = "Password is mandatory")
    val password: String
)
