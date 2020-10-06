package com.dextra.alelo.samples.authentication.controllers

import com.dextra.alelo.samples.authentication.service.AccountUserService
import com.dextra.alelo.samples.authentication.service.AuthenticationService
import com.dextra.alelo.samples.authentication.service.AuthenticationService.Companion.PARTNER_ID_CLAIM
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import mu.KotlinLogging
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@Api(tags = ["Checkout"])
@RequestMapping("/v1/checkout")
class CheckoutController(
    private val accountUserService: AccountUserService,
    private val authenticationService: AuthenticationService
) {

    private val logger = KotlinLogging.logger {}

    @GetMapping("/pages")
    @ApiOperation("Get checkout pages")
    fun getAccountData(
        @RequestHeader("Authentication") authToken: String
    ): String {

        val partnerId = authenticationService.getClaim(PARTNER_ID_CLAIM, authToken)
        val accountId = authenticationService.getClaim(AuthenticationService.ACCOUNT_ID_CLAIM, authToken)

        return "Getting checkout pages for partner $partnerId, with accountId $accountId"
            .also { logger.info { it } }
    }

}
