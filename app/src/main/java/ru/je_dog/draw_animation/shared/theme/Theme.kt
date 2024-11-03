package ru.je_dog.draw_animation.shared.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = AppColor.Green,
    outline = AppColor.White,
    primaryContainer = AppColor.GrayTransparent16,
    background = AppColor.Black,
    onBackground = AppColor.White,
)

private val LightColorScheme = lightColorScheme(
    primary = AppColor.Green,
    outline = AppColor.Black,
    primaryContainer = AppColor.GrayTransparent16,
    background = AppColor.White,
    onBackground = AppColor.Black,
)

@Composable
fun DrawAnimationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}