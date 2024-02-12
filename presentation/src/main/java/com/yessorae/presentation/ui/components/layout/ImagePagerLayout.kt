package com.yessorae.presentation.ui.components.layout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.yessorae.domain.util.MockData
import com.yessorae.presentation.ui.components.item.common.ImageListItem
import com.yessorae.presentation.ui.theme.Dimen
import com.yessorae.presentation.util.compose.Padding

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagePagerLayout(
    modifier: Modifier = Modifier,
    images: List<Any>
) {
    val pagerState = rememberPagerState()
    val imageCount = images.size
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            pageCount = imageCount,
            state = pagerState,
            beyondBoundsPageCount = 1
        ) { index ->
            ImageListItem(
                model = images.getOrNull(index),
                modifier = Modifier.fillMaxSize()
            )
        }

        if (imageCount > 0) {
            Padding(margin = Dimen.space_8)

            PagerIndicator(
                count = imageCount,
                dotSize = Dimen.indicator_dot_size,
                spacedBy = Dimen.space_4,
                currentPage = pagerState.currentPage,
                selectedColor = MaterialTheme.colors.primary,
                unSelectedColor = MaterialTheme.colors.onBackground
            )
        }
    }
}

@Composable
private fun PagerIndicator(
    modifier: Modifier = Modifier,
    count: Int,
    dotSize: Dp,
    spacedBy: Dp,
    currentPage: Int,
    selectedColor: Color,
    unSelectedColor: Color
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(spacedBy)) {
        (0 until count).forEach { index ->
            Box(
                modifier = Modifier
                    .size(dotSize)
                    .background(
                        color = if (index == currentPage) {
                            selectedColor
                        } else {
                            unSelectedColor
                        },
                        shape = CircleShape
                    )
            )
        }
    }
}

@Preview
@Composable
fun ImagePagerLayoutPreview() {
    ImagePagerLayout(
        modifier = Modifier.fillMaxSize(),
        images = listOf(
            MockData.MOCK_IMAGE_URL,
            MockData.MOCK_IMAGE_URL,
            MockData.MOCK_IMAGE_URL
        )
    )
}
