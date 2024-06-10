package com.example.order.model

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "stations")
data class Station(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    @field:NotBlank(message = "Station name is mandatory")
    val station: String
)
