package com.example.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.interfaces.DecodedJWT
import com.example.dto.Account
import com.google.gson.Gson
import java.util.*

object JwtProvider {
    private const val validityInMs = 36_000_00 * 10 // 10 hours
    const val issuer = "ktor-issuer-test"
    const val audience = "ktor-audience-test"

    val verifier: JWTVerifier = JWT
        .require(Ciper.algorithm)
        .withIssuer(issuer)
        .build()

    fun decodeJWT(token: String): DecodedJWT = JWT.require(Ciper.algorithm).build().verify(token)

    fun createJWT(account: Account): String? =
        JWT.create()
            .withIssuedAt(Date())
            .withSubject("Authentication")
            .withIssuer(issuer)
            .withAudience(audience)
            .withClaim("account", Gson().toJson(account))
            .withExpiresAt(Date(System.currentTimeMillis() + validityInMs)).sign(Ciper.algorithm)
}