package com.yessorae.presentation.navigation.destination

import androidx.navigation.NavType
import androidx.navigation.navArgument

object TxtToImgResultDestination : Destination {
    override val route: String = "txt_to_img_result_destination"
    const val requestIdArg: String = "request_id"

    val routeWithArgs = "$route/{$requestIdArg}"
    val arguments = listOf(
        navArgument(requestIdArg) {
            type = NavType.IntType
        }
    )
    fun getRouteWithArgs(requestId: Int) = "$route/$requestId"
}
