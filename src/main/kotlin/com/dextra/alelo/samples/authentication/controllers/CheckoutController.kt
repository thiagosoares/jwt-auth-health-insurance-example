package com.dextra.alelo.samples.authentication.controllers

import com.dextra.alelo.samples.authentication.service.AccountUserService
import com.dextra.alelo.samples.authentication.service.AuthenticationService
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
        @RequestHeader("transaction-id") transactionId: String,
        @RequestHeader("Authentication") authToken: String
    ): String {
        val partnerId = authenticationService.getPartnerIdClaim(authToken)

        logger.info {
            "Getting checkout pages for $partnerId partner, with $transactionId transaction ID"
        }

        return "Getting checkout pages for $partnerId partner, with $transactionId transaction ID"
    }

}
