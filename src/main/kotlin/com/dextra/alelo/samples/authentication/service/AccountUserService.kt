package com.dextra.alelo.samples.authentication.service

import com.dextra.alelo.samples.authentication.model.response.AccountUsers
import org.springframework.stereotype.Component

@Component
class AccountUserService {

    fun getPlanUsers(documentNumber: String): AccountUsers {
        return when (documentNumber) {
            "123" -> getCaptainAmericaPlan()
            "456" -> getThorPlan()
            else -> getCaptainAmericaPlan()
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