package com.shakil.githubreposhowcase.ui.trending_repo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.shakil.githubreposhowcase.ui.theme.GreenApp
import java.io.IOException

@Composable
fun ErrorItem(error: Throwable? = null, onClickRetry: () -> Unit) {
    val animationSpec = remember { LottieAnimationSpec.Asset("retry_lottie.json") }
    val animationState =
        rememberLottieAnimationState(autoPlay = true, repeatCount = Integer.MAX_VALUE)
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 18.dp, start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                animationSpec,
                animationState = animationState,
                modifier = Modifier.width(250.dp).height(250.dp)
            )

            if (error is IOException) {
                Text(
                    text = "No internet connection\nPlease check your internet settings.",
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.onBackground,
                        fontWeight = FontWeight.W800
                    )
                )

            } else {
                Text(
                    text = "Something went wrong..",
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.onBackground,
                        fontWeight = FontWeight.W800
                    )
                )
            }
            Spacer(modifier = Modifier.width(100.dp).height(10.dp))
            Text(
                text = "An alien is probably blocking your signal",
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.3f),
                    fontWeight = FontWeight.W800
                )
            )
            Spacer(modifier = Modifier.width(100.dp).height(32.dp))
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(0.8f),
                border = BorderStroke(1.dp, GreenApp),
                shape = RoundedCornerShape(topStart = 6.dp,topEnd = 6.dp,bottomStart = 6.dp,bottomEnd = 6.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = GreenApp),
                onClick = { onClickRetry() }) {
                Text(text = "RETRY")
            }

        }

    }

}