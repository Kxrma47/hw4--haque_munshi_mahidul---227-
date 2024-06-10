package com.example.auth.service

import com.example.auth.model.User
import com.example.auth.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) : UserDetailsService {

    private val logger = LoggerFactory.getLogger(UserService::class.java)

    fun getUserByEmail(email: String): User {
        return userRepository.findByEmail(email)?.also {
            logger.info("User with email {} found", email)
        } ?: run {
            logger.error("User with email {} not found", email)
            throw UserNotFoundException("User not found with email: $email")
        }
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username)?.also {
            logger.info("User with username {} found", username)
        } ?: run {
            logger.error("User with username {} not found", username)
            throw UsernameNotFoundException("User not found with username: $username")
        }
        return org.springframework.security.core.userdetails.User(user.email, user.password, emptyList())
    }

    class UserNotFoundException(message: String) : RuntimeException(message)
}
