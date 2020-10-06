package com.dextra.alelo.samples.authentication.service

import com.dextra.alelo.samples.authentication.exception.UnauthorizedException
import com.dextra.alelo.samples.authentication.model.response.AccountData
import com.dextra.alelo.samples.authentication.model.response.AccountUsers
import org.springframework.stereotype.Component

@Component
class AccountUserService {

    fun getAccountData(partnerId: String, type: String): AccountData {
        verifyTokenType(type)
        return AccountData(
            status = "Active",
            partner = partnerId
        )
    }

    fun getPlanUsers(partnerId: String, type: String): AccountUsers {
        verifyTokenType(type)
        return when (partnerId) {
            "123" -> getCaptainAmericaPlan()
            "456" -> getThorPlan()
            else -> getCaptainAmericaPlan()
        }
    }

    private fun verifyTokenType(type: String) {
        if ("Partner" != type) {
            throw UnauthorizedException("Invalid token type")
        }
    }

    private fun getCaptainAmericaPlan() = AccountUsers(
        planHolder = AccountUsers.PlanHolder("Captain America"),
        dependents = listOf(
            AccountUsers.Dependent("Iron Man"),
            AccountUsers.Dependent("Spider-Man")
        )
    )

    private fun getThorPlan() = AccountUsers(
        planHolder = AccountUsers.PlanHolder("Thor"),
        dependents = listOf(
            AccountUsers.Dependent("Loki"),
            AccountUsers.Dependent("Hela")
        )
    )

}