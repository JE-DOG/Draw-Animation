package ru.je_dog.draw_animation.ui.canvas.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import ru.je_dog.draw_animation.core.compose.preview.DefaultPreview
import ru.je_dog.draw_animation.shared.theme.DrawAnimationTheme
import ru.je_dog.draw_animation.ui.canvas.viewmodel.CanvasAction

@Composable
fun SetColorDialogContent(
    modifier: Modifier = Modifier,
    onAction: (CanvasAction) -> Unit = {},
) {
    Row(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)
            .padding(5.dp),
    ) {
        val colors = remember {
            listOf(
                Color.Red,
                Color.Yellow,
                Color.Blue,
            )
        }

        ColorsRow(
            colors = colors,
            onItemClick = { color ->
                val action = CanvasAction.DrawPropertyManage.SetColor(color)
                onAction(action)
            }
        )
    }
}

@Composable
private fun ColorsRow(
    colors: List<Color>,
    modifier: Modifier = Modifier,
    onItemClick: (Color) -> Unit = {},
) {
    Row(
        modifier = modifier,
    ) {
        colors.fastForEach { color ->
            ColorItem(
                color = color,
                onClick = onItemClick,
            )
        }
    }
}

@Composable
private fun ColorItem(
    color: Color,
    modifier: Modifier = Modifier,
    onClick: (Color) -> Unit = {},
) {
    IconButton(
        modifier = modifier,
        onClick = { onClick(color) },
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .background(
                    color = color,
                    shape = CircleShape,
                ),
        )
    }
}

//region Preview
@Composable
@DefaultPreview
private fun SetColorDialogContentPreview() {
    DrawAnimationTheme {
        SetColorDialogContent()
    }
}
//endregion
