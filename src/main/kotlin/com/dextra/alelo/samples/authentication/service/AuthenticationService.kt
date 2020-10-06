package com.dextra.alelo.samples.authentication.service

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.algorithms.Algorithm
import com.dextra.alelo.samples.authentication.model.request.PartnerLogin
import com.dextra.alelo.samples.authentication.model.request.AccountLogin
import com.dextra.alelo.samples.authentication.model.response.LoginResponse
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.ZoneId
import java.util.Date

@Component
class AuthenticationService {

    fun getPartnerAuthenticationToken(login: PartnerLogin): LoginResponse {

        val jwtBuilder = getJwtBuilder()
            .withClaim(PARTNER_ID_CLAIM, login.username)
            .withClaim(TOKEN_TYPE_ID_CLAIM, "Partner")
            .sign(getAlgorithm())

        return LoginResponse(
            token = jwtBuilder
        )
    }

    fun getAccountAuthenticationToken(login: AccountLogin, authToken: String): LoginResponse {

        val partnerId = getClaim(PARTNER_ID_CLAIM, authToken)

        val jwtBuilder = getJwtBuilder()
            .withClaim(PARTNER_ID_CLAIM, partnerId)
            .withClaim(ACCOUNT_ID_CLAIM, login.accountId)
            .withClaim(TOKEN_TYPE_ID_CLAIM, "Account")
            .sign(getAlgorithm())

        return LoginResponse(
            token = jwtBuilder
        )
    }

    fun getClaim(claimId: String, token: String): String {
        return  JWT.require(getAlgorithm())
            .build()
            .verify(token.replace("Bearer ", ""))
            .getClaim(claimId)
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
        val expiration = Instant.now().plusSeconds(180)
        return Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant())
    }

    private fun getAlgorithm() = Algorithm.HMAC256(DEFAULT_SIGN_KEY)

    companion object {
        const val DEFAULT_SIGN_KEY = "dextra"
        const val DEFAULT_ISSUER = "dextra.com.br"
        const val DEFAULT_SUBJECT = "dextra"
        const val ACCOUNT_ID_CLAIM = "accountId"
        const val PARTNER_ID_CLAIM = "partnerId"
        const val TOKEN_TYPE_ID_CLAIM = "type"
    }
}