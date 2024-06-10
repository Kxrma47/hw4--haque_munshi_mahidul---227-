package com.example.auth.model

import org.springframework.http.HttpStatus

data class ApiResponse<T>(
    val status: HttpStatus,
    val message: String,
    val data: T?
)
