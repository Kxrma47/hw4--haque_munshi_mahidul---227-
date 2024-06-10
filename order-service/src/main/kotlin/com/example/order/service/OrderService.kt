package com.example.order.service

import com.example.order.model.Order
import com.example.order.repository.OrderRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class OrderService(private val orderRepository: OrderRepository) {
    private val logger = LoggerFactory.getLogger(OrderService::class.java)

    fun createOrder(order: Order): Order {
        return orderRepository.save(order).also {
            logger.info("Order created: {}", it)
        }
    }

    fun updateOrderStatus(orderId: Long, status: Int): Order {
        val order = orderRepository.findById(orderId).orElseThrow {
            logger.error("Order not found: {}", orderId)
            Exception("Order not found")
        }
        order.status = status
        return orderRepository.save(order).also {
            logger.info("Order status updated: {}", it)
        }
    }

    fun getOrderById(orderId: Long): Order? {
        return orderRepository.findById(orderId).orElse(null).also {
            if (it != null) {
                logger.info("Order retrieved: {}", it)
            } else {
                logger.warn("Order not found: {}", orderId)
            }
        }
    }

    fun getAllOrders(): List<Order> {
        return orderRepository.findAll().also {
            logger.info("All orders retrieved, count: {}", it.size)
        }
    }
}
