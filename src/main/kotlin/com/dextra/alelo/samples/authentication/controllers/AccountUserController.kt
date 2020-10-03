package com.dextra.alelo.samples.authentication.controllers

import com.dextra.alelo.samples.authentication.model.response.AccountUsers
import com.dextra.alelo.samples.authentication.service.AccountUserService
import com.dextra.alelo.samples.authentication.service.AuthenticationService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Validated
@RestController
@Api(tags = ["Account"])
@RequestMapping("/v1/account")
class AccountUserController(
    private val accountUserService: AccountUserService,
    private val authenticationService: AuthenticationService
) {

    @GetMapping("/account-users")
    @ApiOperation("Get Partner Authentication Token")
    fun getPartnerAuthenticationToken(
        @RequestHeader("Authentication") authToken: String
    ): AccountUsers {
        val documentNumber = authenticationService.getDocumentNumberClaim(authToken)
        return accountUserService.getPlanUsers(documentNumber)
    }

}
