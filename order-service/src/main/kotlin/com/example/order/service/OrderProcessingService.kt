package com.example.order.service

import com.example.order.repository.OrderRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderProcessingService(private val orderRepository: OrderRepository) {

    private val logger = LoggerFactory.getLogger(OrderProcessingService::class.java)

    @Scheduled(fixedDelay = 10000) // Run every 10 seconds
    fun processOrders() {
        val orders = orderRepository.findAll()
        orders.forEach { order ->
            if (order.status == 1) { // Check status
                val newStatus = if (Random().nextBoolean()) 2 else 3 // Randomly success or reject
                order.status = newStatus
                try {
                    orderRepository.save(order)
                    logger.info("Order ID: ${order.id} status updated to $newStatus")
                } catch (e: Exception) {
                    logger.error("Failed to update order ID: ${order.id}", e)
                }
            }
        }
    }
}
