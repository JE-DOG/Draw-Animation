package ru.je_dog.draw_animation.ui.canvas.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import ru.je_dog.draw_animation.R
import ru.je_dog.draw_animation.core.compose.preview.DefaultPreview
import ru.je_dog.draw_animation.shared.theme.DrawAnimationTheme
import ru.je_dog.draw_animation.ui.canvas.viewmodel.CanvasAction

@Composable
fun SetColorDialogContent(
    modifier: Modifier = Modifier,
    onAction: (CanvasAction) -> Unit = {},
) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)
            .padding(5.dp)
            .padding(horizontal = 16.dp),
    ) {
        val colors = remember {
            listOf(
                Color.Red,
                Color.Yellow,
                Color.Blue,
            )
        }

        ColorsRow(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = colors,
            onItemClick = { color ->
                val action = CanvasAction.DrawPropertyManage.SetColor(color)
                onAction(action)
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        CustomColor(
            onConfirmClick = { color ->
                val action = CanvasAction.DrawPropertyManage.SetColor(color)
                onAction(action)
            }
        )
    }
}

@Composable
private fun CustomColor(
    modifier: Modifier = Modifier,
    onConfirmClick: (Color) -> Unit,
) {
    var red by remember {
        mutableStateOf(0)
    }
    var green by remember {
        mutableStateOf(0)
    }
    var blue by remember {
        mutableStateOf(0)
    }

    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                value = red.toString(),
                label = {
                    Text(
                        text = stringResource(id = R.string.canvas_dialog_color_red_placeholder),
                        color = Color.LightGray,
                    )
                },
                onValueChange = { value ->
                    val newRed = value.toIntOrNull()?.coerceIn(0..255) ?: return@TextField
                    red = newRed
                },
            )

            Spacer(modifier = Modifier.width(10.dp))

            TextField(
                modifier = Modifier.weight(1f),
                value = blue.toString(),
                label = {
                    Text(
                        text = stringResource(id = R.string.canvas_dialog_color_blue_placeholder),
                        color = Color.LightGray,
                    )
                },
                onValueChange = { value ->
                    val newBlue = value.toIntOrNull()?.coerceIn(0..255) ?: return@TextField
                    blue = newBlue
                },
            )

            Spacer(modifier = Modifier.width(10.dp))

            TextField(
                modifier = Modifier.weight(1f),
                value = green.toString(),
                label = {
                    Text(
                        text = stringResource(id = R.string.canvas_dialog_color_green_placeholder),
                        color = Color.LightGray,
                    )
                },
                onValueChange = { value ->
                    val newGreen = value.toIntOrNull()?.coerceIn(0..255) ?: return@TextField
                    green = newGreen
                },
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val color = Color(
                    red = red,
                    green = green,
                    blue = blue,
                )
                onConfirmClick(color)
            }
        ) {
            Text(text = stringResource(id = R.string.canvas_dialog_color_confirm_custom_color))
        }
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
