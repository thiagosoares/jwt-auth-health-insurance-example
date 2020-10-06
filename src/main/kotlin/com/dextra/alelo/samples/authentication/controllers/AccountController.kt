package com.dextra.alelo.samples.authentication.controllers

import com.dextra.alelo.samples.authentication.model.response.AccountData
import com.dextra.alelo.samples.authentication.model.response.AccountUsers
import com.dextra.alelo.samples.authentication.service.AccountUserService
import com.dextra.alelo.samples.authentication.service.AuthenticationService
import com.dextra.alelo.samples.authentication.service.AuthenticationService.Companion.PARTNER_ID_CLAIM
import com.dextra.alelo.samples.authentication.service.AuthenticationService.Companion.TOKEN_TYPE_ID_CLAIM
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
    @ApiOperation(
        value = "Get Account Data",
        notes = "Get the account data using the Partner token ant the common accountID"
    )
    fun getAccountData(
        @PathVariable accountId: String,
        @RequestHeader("Authentication") accountToken: String
    ): AccountData {
        val type = authenticationService.getClaim(TOKEN_TYPE_ID_CLAIM, accountToken)
        val partnerId = authenticationService.getClaim(PARTNER_ID_CLAIM, accountToken)

        logger.info {
            "Getting $accountId account data for $partnerId partner"
        }

        return accountUserService.getAccountData(accountId, type)
    }

    @GetMapping("/{accountId}/users")
    @ApiOperation(
        value = "Get Account Users",
        notes = "Get the account user using the Partner token ant the common accountID"
    )
    fun getAccountUsers(
        @PathVariable accountId: String,
        @RequestHeader("Authentication") accountToken: String
    ): AccountUsers {
        val type = authenticationService.getClaim(TOKEN_TYPE_ID_CLAIM, accountToken)
        val partnerId = authenticationService.getClaim(PARTNER_ID_CLAIM, accountToken)

        return accountUserService.getPlanUsers(accountId, type).also {
            logger.info {
                "Getting $accountId account users for $partnerId partner"
            }
        }
    }

}
