package com.shakil.githubreposhowcase.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White

private val DarkColorPalette = darkColors(
    primary = Black,
    primaryVariant = Black,
    secondary = Black,
    onBackground = White,
    onSurface = White,
)

private val LightColorPalette = lightColors(
    primary = White,
    primaryVariant = White,
    secondary = White,
    background = White,
    onSurface = Black,
)

@Composable
fun GithubRepoShowcaseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}