package com.example.auth.model

import javax.persistence.*

@Entity
@Table(name = "session")
data class Session(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(nullable = false)
    val token: String,

    @Column(nullable = false)
    val expires: java.sql.Timestamp
)
