package com.dextra.alelo.samples.authentication.model.response

data class AccountUsers(
    val planHolder: PlanHolder,
    val dependents: List<Dependent>
) {

    data class PlanHolder(val name: String)
    data class Dependent(val name: String)

}