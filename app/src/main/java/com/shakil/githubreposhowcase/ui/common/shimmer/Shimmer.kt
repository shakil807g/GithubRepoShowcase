package com.shakil.githubreposhowcase.ui.common.shimmer

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class ShimmerAnimationType {
    FADE, TRANSLATE, FADETRANSLATE, VERTICAL
}

@Preview
@Composable
fun ShimmerList() {
    val shimmerAnimationType by remember { mutableStateOf(ShimmerAnimationType.FADETRANSLATE) }

    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 100f,
        targetValue = 600f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = LinearEasing),
            RepeatMode.Restart
        )
    )

    val colorAnim by transition.animateColor(
        initialValue = Color.LightGray.copy(alpha = 0.6f),
        targetValue = Color.LightGray,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Restart
        )
    )


    val list = if (shimmerAnimationType != ShimmerAnimationType.TRANSLATE) {
        listOf(colorAnim, colorAnim.copy(alpha = 0.5f))
    } else {
        listOf(Color.LightGray.copy(alpha = 0.6f), Color.LightGray)
    }

    val dpValue = if (shimmerAnimationType != ShimmerAnimationType.FADE) {
        translateAnim.dp
    } else {
        2000.dp
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        repeat(20) {
            ShimmerItem(list, dpValue.value, shimmerAnimationType == ShimmerAnimationType.VERTICAL)
        }
    }
}

@Composable
fun ShimmerItem(lists: List<Color>, floatAnim: Float = 0f, isVertical: Boolean) {
    val brush = if (isVertical) Brush.verticalGradient(lists, 0f, floatAnim) else
        Brush.horizontalGradient(lists, 0f, floatAnim)
    Column(modifier = Modifier.fillMaxWidth()) {

        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Spacer(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(brush = brush)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(30.dp)
                        .padding(8.dp)
                        .background(brush = brush)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .padding(8.dp)
                        .background(brush = brush)
                )
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .padding(start = 32.dp)
                .background(Color.LightGray.copy(alpha = 0.5f))
        )
    }

}
