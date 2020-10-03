package com.dextra.alelo.samples.authentication.service

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.algorithms.Algorithm.HMAC512
import com.auth0.jwt.exceptions.SignatureVerificationException
import com.dextra.alelo.samples.authentication.exception.UnauthorizedException
import com.dextra.alelo.samples.authentication.model.request.PartnerLogin
import com.dextra.alelo.samples.authentication.model.request.UserLogin
import com.dextra.alelo.samples.authentication.model.response.LoginResponse
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.ZoneId
import java.util.Date

@Component
class AuthenticationService {

    fun getPartnerAuthenticationToken(login: PartnerLogin): LoginResponse {

        val jwtBuilder = getJwtBuilder()
            .sign(HMAC512(DEFAULT_SIGN_KEY.toByteArray()))

        return LoginResponse(
            token = jwtBuilder
        )
    }

    fun getCustomerAuthenticationToken(login: UserLogin, authToken: String): LoginResponse {

        verifyToken(authToken)

        val jwtBuilder = getJwtBuilder()
            .withClaim(USER_ID_CLAIM, login.documentNumber)
            .sign(HMAC512(DEFAULT_SIGN_KEY.toByteArray()))

        return LoginResponse(
            token = jwtBuilder
        )
    }

    fun verifyToken(token: String) {
        try {
            JWT.require(HMAC512(DEFAULT_SIGN_KEY.toByteArray()))
                .build()
                .verify(token.replace("Bearer ", ""))
        } catch (e: SignatureVerificationException) {
            throw UnauthorizedException("Invalid Token")
        }
    }

    fun getDocumentNumberClaim(token: String): String {
        return  JWT.require(HMAC512(DEFAULT_SIGN_KEY.toByteArray()))
            .build()
            .verify(token.replace("Bearer ", ""))
            .getClaim(USER_ID_CLAIM)
            .asString()
    }

    private fun getJwtBuilder(): JWTCreator.Builder {
        return JWT.create()
            .withIssuer(DEFAULT_ISSUER)
            .withSubject(DEFAULT_SUBJECT)
            .withNotBefore(Date())
            .withIssuedAt(Date())
            .withExpiresAt(getExpiresAt())
    }

    private fun getExpiresAt(): Date? {
        val expiration = Instant.now().plusSeconds(60)
        return Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant())
    }

    companion object {
        const val DEFAULT_SIGN_KEY = "dextra"
        const val DEFAULT_ISSUER = "dextra.com.br"
        const val DEFAULT_SUBJECT = "dextra"
        const val USER_ID_CLAIM = "userId"
    }
}