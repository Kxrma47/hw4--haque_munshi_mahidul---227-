package com.example.order.controller

import com.example.order.model.ApiResponse
import com.example.order.model.Order
import com.example.order.service.OrderService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/orders")
class OrderController(private val orderService: OrderService) {

    private val logger = LoggerFactory.getLogger(OrderController::class.java)

    @PostMapping("/create")
    fun createOrder(@Valid @RequestBody order: Order): ResponseEntity<ApiResponse<Order>> {
        return try {
            val createdOrder = orderService.createOrder(order)
            ResponseEntity(ApiResponse(HttpStatus.CREATED, "Order created successfully", createdOrder), HttpStatus.CREATED)
        } catch (e: Exception) {
            logger.error("Failed to create order: ${e.message}", e)
            ResponseEntity(ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create order", null), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/{id}")
    fun getOrder(@PathVariable id: Long): ResponseEntity<ApiResponse<Order?>> {
        return try {
            val order = orderService.getOrderById(id)
            if (order != null) {
                ResponseEntity(ApiResponse(HttpStatus.OK, "Order retrieved successfully", order), HttpStatus.OK)
            } else {
                ResponseEntity(ApiResponse(HttpStatus.NOT_FOUND, "Order not found", null), HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            logger.error("Failed to retrieve order: ${e.message}", e)
            ResponseEntity(ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve order", null), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping
    fun getAllOrders(): ResponseEntity<ApiResponse<List<Order>>> {
        return try {
            val orders = orderService.getAllOrders()
            ResponseEntity(ApiResponse(HttpStatus.OK, "Orders retrieved successfully", orders), HttpStatus.OK)
        } catch (e: Exception) {
            logger.error("Failed to retrieve orders: ${e.message}", e)
            ResponseEntity(ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve orders", null), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
