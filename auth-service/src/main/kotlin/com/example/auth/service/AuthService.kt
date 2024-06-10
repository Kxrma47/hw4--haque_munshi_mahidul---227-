package com.example.auth.service

import com.example.auth.config.JwtUtil
import com.example.auth.model.Session
import com.example.auth.model.User
import com.example.auth.repository.SessionRepository
import com.example.auth.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.util.regex.Pattern

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository,
    private val jwtUtil: JwtUtil
) {
    private val logger = LoggerFactory.getLogger(AuthService::class.java)
    private val emailPattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private val passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")

    fun registerUser(user: User): User {
        if (userRepository.existsByEmail(user.email)) {
            logger.error("Registration failed: Email {} already registered", user.email)
            throw IllegalArgumentException("Email already registered")
        }
        validateUserData(user)
        val encodedPassword = BCryptPasswordEncoder().encode(user.password)
        val newUser = user.copy(password = encodedPassword, created = Timestamp(System.currentTimeMillis()))
        return userRepository.save(newUser).also {
            logger.info("User {} registered successfully", it.email)
        }
    }

    fun loginUser(email: String, password: String): String {
        logger.info("Attempting login for user: {}", email)
        val user = userRepository.findByEmail(email) ?: run {
            logger.error("Login failed: User with email {} not found", email)
            throw IllegalArgumentException("User not found")
        }
        if (!BCryptPasswordEncoder().matches(password, user.password)) {
            logger.error("Login failed: Invalid credentials for user {}", email)
            throw IllegalArgumentException("Invalid credentials")
        }
        logger.info("Credentials verified for user: {}", email)
        val token = jwtUtil.generateToken(user)
        logger.info("Generated token: {}", token)
        val expires = Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 10) // 10 hours
        val session = Session(user = user, token = token, expires = expires)
        sessionRepository.save(session)
        logger.info("User {} logged in successfully with token {}", email, token)
        return token
    }


    private fun validateUserData(user: User) {
        if (!emailPattern.matcher(user.email).matches()) {
            logger.error("Invalid email format: {}", user.email)
            throw IllegalArgumentException("Invalid email format")
        }
        if (!passwordPattern.matcher(user.password).matches()) {
            logger.error("Invalid password format for user: {}", user.email)
            throw IllegalArgumentException("Password must be at least 8 characters long, include upper and lower case letters, numbers, and special characters")
        }
    }
}
