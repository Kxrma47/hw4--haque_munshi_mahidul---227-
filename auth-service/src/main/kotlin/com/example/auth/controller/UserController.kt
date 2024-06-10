package com.example.auth.controller

import com.example.auth.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

@RestController
@RequestMapping("/api/auth")
class UserController(private val userService: UserService) {

    @GetMapping("/user")
    fun getUserDetails(): ResponseEntity<Any> {
        return try {
            val authentication = SecurityContextHolder.getContext().authentication
            val userDetails = authentication.principal as? UserDetails ?: throw Exception("Invalid token")
            val user = userService.getUserByEmail(userDetails.username)
            ResponseEntity(user, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(mapOf("status" to 401, "error" to "Unauthorized", "message" to "Invalid token"), HttpStatus.UNAUTHORIZED)
        }
    }
}
