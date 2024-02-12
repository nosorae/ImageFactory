package com.yessorae.presentation.navigation.destination

import com.yessorae.presentation.R
import com.yessorae.presentation.util.StringModel
import com.yessorae.presentation.util.TextString

object MainDestination : Destination {
    override val route: String = "main"
    val screens: List<MainBottomTabDestination> = listOf(
        TxtToImgBottomTabDestination,
        InpaintingBottomTabDestination,
        HistoryBottomTabDestination
    )
}

interface MainBottomTabDestination : Destination {
    val icon: Int
    val display: StringModel
}

object TxtToImgBottomTabDestination : MainBottomTabDestination {
    override val icon: Int = R.drawable.ic_bottom_nav_text_to_image_24_dp
    override val route: String = "text_to_image_bottom_tab_destination"
    override val display: StringModel = TextString("Text-to-Image")
}

object InpaintingBottomTabDestination : MainBottomTabDestination {
    override val icon: Int = R.drawable.ic_bottom_nav_inpainting_24_dp
    override val route: String = "inpainting_bottom_tab_destination"
    override val display: StringModel = TextString("Inpainting")
}

object HistoryBottomTabDestination : MainBottomTabDestination {
    override val icon: Int = R.drawable.ic_bottom_nav_history_24_dp
    override val route: String = "profile_bottom_tab_destination"
    override val display: StringModel = TextString("History")
}
