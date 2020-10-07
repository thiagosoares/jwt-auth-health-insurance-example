package com.dextra.alelo.samples.authentication.controllers

import com.dextra.alelo.samples.authentication.model.request.PartnerLogin
import com.dextra.alelo.samples.authentication.model.request.AccountLogin
import com.dextra.alelo.samples.authentication.model.response.LoginResponse
import com.dextra.alelo.samples.authentication.service.AuthenticationService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@Api(tags = ["Authentication"])
@RequestMapping("/v1/auth")
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {

    @PostMapping("/login")
    @ApiOperation(
        value = "Get Partner Authentication Token",
        notes = "Partner token with partner id"
    )
    fun getPartnerAuthenticationToken(
        @RequestBody login: PartnerLogin
    ): LoginResponse {
        return authenticationService.getPartnerAuthenticationToken(login)
    }

    @PostMapping("/account/login")
    @ApiOperation(
        value = "Get a Account Authentication Token",
        notes = "Account token. Identify a Account specifically. Use the common transactionId to identify which account is requested.  Contains a partnerId and accountId "
    )
    fun getAccountAuthenticationToken(
        @RequestBody login: AccountLogin,
        @RequestHeader("Authentication") @ApiParam(value = "Partner Token") authToken: String
    ): LoginResponse {
        return authenticationService.getAccountAuthenticationToken(login, authToken)
    }
}
