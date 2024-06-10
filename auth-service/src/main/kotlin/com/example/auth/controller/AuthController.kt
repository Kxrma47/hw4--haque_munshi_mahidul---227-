package com.example.auth.controller

import com.example.auth.model.ApiResponse
import com.example.auth.model.LoginRequest
import com.example.auth.model.User
import com.example.auth.service.AuthService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    private val logger = LoggerFactory.getLogger(AuthController::class.java)

    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody user: User): ResponseEntity<ApiResponse<Long>> {
        logger.info("Received registration request for user: ${user.email}")
        return try {
            val registeredUser = authService.registerUser(user)
            ResponseEntity(ApiResponse(HttpStatus.CREATED, "User registered successfully", registeredUser.id), HttpStatus.CREATED)
        } catch (e: Exception) {
            logger.error("Registration failed: ${e.message}")
            ResponseEntity(ApiResponse(HttpStatus.BAD_REQUEST, e.message ?: "Error during registration", null), HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/login")
    fun loginUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<ApiResponse<String>> {
        logger.info("Received login request for user: ${loginRequest.email}")
        return try {
            val token = authService.loginUser(loginRequest.email, loginRequest.password)
            logger.info("Login successful, Token: $token")
            ResponseEntity(ApiResponse(HttpStatus.OK, "Login successful", token), HttpStatus.OK)
        } catch (e: Exception) {
            logger.error("Login failed: ${e.message}")
            ResponseEntity(ApiResponse(HttpStatus.UNAUTHORIZED, e.message ?: "Error during login", null), HttpStatus.UNAUTHORIZED)
        }
    }
}
