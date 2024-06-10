package com.example.auth.model

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = ["email"])])
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotBlank(message = "Nickname is mandatory")
    @Column(nullable = false)
    val nickname: String,

    @field:Email(message = "Email should be valid")
    @field:NotBlank(message = "Email is mandatory")
    @Column(nullable = false, unique = true)
    val email: String,

    @field:Size(min = 8, message = "Password should be at least 8 characters long")
    @field:Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=!])(?=\\S+\$).{8,}\$",
        message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and have no whitespace"
    )
    @field:NotBlank(message = "Password is mandatory")
    @Column(nullable = false)
    var password: String,

    @Column(nullable = false, updatable = false)
    val created: java.sql.Timestamp = java.sql.Timestamp(System.currentTimeMillis())
)
