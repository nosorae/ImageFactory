package com.yessorae.presentation.ui.screen.main.inpainting.model

import androidx.annotation.StringRes
import com.yessorae.presentation.R
import com.yessorae.presentation.util.helper.ImageSegmentationHelperImpl.Companion.CATEGORY_BACKGROUND
import com.yessorae.presentation.util.helper.ImageSegmentationHelperImpl.Companion.CATEGORY_BODY_SKIN
import com.yessorae.presentation.util.helper.ImageSegmentationHelperImpl.Companion.CATEGORY_CLOTHES
import com.yessorae.presentation.util.helper.ImageSegmentationHelperImpl.Companion.CATEGORY_ETC
import com.yessorae.presentation.util.helper.ImageSegmentationHelperImpl.Companion.CATEGORY_FACE_SKIN
import com.yessorae.presentation.util.helper.ImageSegmentationHelperImpl.Companion.CATEGORY_HAIR

enum class SegmentationLabel(val index: UInt?, @StringRes val resId: Int) {
    BACKGROUND(CATEGORY_BACKGROUND, R.string.in_painting_segmentation_option_background),
    HAIR(CATEGORY_HAIR, R.string.in_painting_segmentation_option_hair),
    BODY(CATEGORY_BODY_SKIN, R.string.in_painting_segmentation_option_body),
    FACE(CATEGORY_FACE_SKIN, R.string.in_painting_segmentation_option_face),
    CLOTHES(CATEGORY_CLOTHES, R.string.in_painting_segmentation_option_clothes),
    ETC(CATEGORY_ETC, R.string.in_painting_segmentation_option_etc),
    ALL(null, R.string.in_painting_segmentation_option_all)
}
