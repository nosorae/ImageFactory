package com.yessorae.presentation.navigation.destination

import androidx.navigation.NavType
import androidx.navigation.navArgument

object InPaintingResultDestination : Destination {
    override val route: String = "in_painting_result_destination"
    const val requestIdArg: String = "request_id"

    val routeWithArgs = "$route/{$requestIdArg}"
    val arguments = listOf(
        navArgument(requestIdArg) {
            type = NavType.IntType
        }
    )
    fun getRouteWithArgs(requestId: Int) = "$route/$requestId"
}