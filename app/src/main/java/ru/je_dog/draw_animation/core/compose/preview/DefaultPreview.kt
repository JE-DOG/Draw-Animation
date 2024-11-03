package ru.je_dog.draw_animation.core.compose.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Target(
    AnnotationTarget.FUNCTION,
)
@Preview(
    name = "Light",
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark",
    fontScale = 2f,
)
annotation class DefaultPreview
