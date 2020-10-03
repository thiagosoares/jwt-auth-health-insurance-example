package com.dextra.alelo.samples.authentication.controllers

import com.dextra.alelo.samples.authentication.model.response.AccountData
import com.dextra.alelo.samples.authentication.model.response.AccountUsers
import com.dextra.alelo.samples.authentication.service.AccountUserService
import com.dextra.alelo.samples.authentication.service.AuthenticationService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import mu.KotlinLogging
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@Api(tags = ["Account"])
@RequestMapping("/v1/account")
class AccountController(
    private val accountUserService: AccountUserService,
    private val authenticationService: AuthenticationService
) {

    private val logger = KotlinLogging.logger {}

    @GetMapping("/{accountId}")
    @ApiOperation("Get Account Data")
    fun getAccountData(
        @PathVariable accountId: String,
        @RequestHeader("Authentication") authToken: String
    ): AccountData {
        val partnerId = authenticationService.getPartnerIdClaim(authToken)

        logger.info {
            "Getting $accountId account data for $partnerId partner"
        }

        return accountUserService.getAccountData(accountId)
    }

    @GetMapping("/{accountId}/users")
    @ApiOperation("Get Account Users")
    fun getAccountUsers(
        @PathVariable accountId: String,
        @RequestHeader("Authentication") authToken: String
    ): AccountUsers {
        val partnerId = authenticationService.getPartnerIdClaim(authToken)

        logger.info {
            "Getting $accountId account users for $partnerId partner"
        }

        return accountUserService.getPlanUsers(accountId)
    }

}
