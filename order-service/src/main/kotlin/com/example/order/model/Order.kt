package com.example.order.model

import java.sql.Timestamp
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity
@Table(name = "orders")
data class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    @field:NotNull(message = "User ID is required")
    @field:Min(1, message = "User ID must be greater than 0")
    val userId: Long,

    @Column(nullable = false)
    @field:NotNull(message = "From Station ID is required")
    @field:Min(1, message = "From Station ID must be greater than 0")
    val fromStationId: Long,

    @Column(nullable = false)
    @field:NotNull(message = "To Station ID is required")
    @field:Min(1, message = "To Station ID must be greater than 0")
    val toStationId: Long,

    @Column(nullable = false)
    @field:NotNull(message = "Status is required")
    var status: Int, // 1 - check, 2 - success, 3 - rejection

    @Column(nullable = false, updatable = false)
    val created: Timestamp = Timestamp(System.currentTimeMillis())
)
