package com.example.jwt

import com.auth0.jwt.algorithms.Algorithm

object Ciper {
    private val secretKey = "something-secret-vcl"
    private val payloadKey = "omg-secret-payload"
    val algorithm: Algorithm = Algorithm.HMAC256(secretKey)
    fun encrypt(data: String?): ByteArray =
        algorithm.sign(data?.toByteArray(), payloadKey.toByteArray())
}