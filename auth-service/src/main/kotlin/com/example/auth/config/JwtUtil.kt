package com.example.auth.config

import com.example.auth.model.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtUtil {

    private val secretKey: Key = Keys.secretKeyFor(SignatureAlgorithm.HS512)
    private val logger = LoggerFactory.getLogger(JwtUtil::class.java)

    fun generateToken(user: User): String {
        val claims = Jwts.claims().setSubject(user.email).apply {
            this["userId"] = user.id
            this["nickname"] = user.nickname
        }

        return try {
            Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date())
                .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(secretKey)
                .compact().also {
                    logger.info("Token generated for user: ${user.email}, Token: $it")
                }
        } catch (e: Exception) {
            logger.error("Error generating token for user: ${user.email}", e)
            throw RuntimeException("Token generation failed")
        }
    }

    fun getEmailFromToken(token: String): String {
        return try {
            getClaimsFromToken(token).subject
        } catch (e: Exception) {
            logger.error("Error extracting email from token", e)
            throw RuntimeException("Token parsing failed")
        }
    }

    fun validateToken(token: String): Boolean {
        return try {
            val claims = getClaimsFromToken(token)
            val expirationDate = claims.expiration
            !expirationDate.before(Date()).also {
                if (!it) logger.warn("Token expired: $token")
            }
        } catch (e: Exception) {
            logger.error("Token validation failed", e)
            false
        }
    }

    private fun getClaimsFromToken(token: String): Claims {
        return try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body
        } catch (e: Exception) {
            logger.error("Error parsing token: $token", e)
            throw RuntimeException("Token parsing failed")
        }
    }
}
