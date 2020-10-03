package com.dextra.alelo.samples.authentication.controllers

import com.dextra.alelo.samples.authentication.model.request.PartnerLogin
import com.dextra.alelo.samples.authentication.model.request.UserLogin
import com.dextra.alelo.samples.authentication.model.response.LoginResponse
import com.dextra.alelo.samples.authentication.service.AuthenticationService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
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
    @ApiOperation("Get Partner Authentication Token")
    fun getPartnerAuthenticationToken(
        @RequestBody login: PartnerLogin
    ): LoginResponse {
        return authenticationService.getPartnerAuthenticationToken(login)
    }

    @PostMapping("/customer/login")
    @ApiOperation("Get Customer Authentication Token")
    fun getCustomerAuthenticationToken(
        @RequestBody login: UserLogin,
        @RequestHeader("Authentication") authToken: String
    ): LoginResponse {
        return authenticationService.getCustomerAuthenticationToken(login, authToken)
    }
}
