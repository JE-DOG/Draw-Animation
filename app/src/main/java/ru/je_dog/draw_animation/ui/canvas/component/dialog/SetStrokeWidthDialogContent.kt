package ru.je_dog.draw_animation.ui.canvas.component.dialog

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.je_dog.draw_animation.R
import ru.je_dog.draw_animation.core.compose.preview.DefaultPreview
import ru.je_dog.draw_animation.shared.theme.DrawAnimationTheme
import ru.je_dog.draw_animation.ui.canvas.model.DrawProperty
import ru.je_dog.draw_animation.ui.canvas.viewmodel.CanvasAction
import ru.je_dog.draw_animation.ui.canvas.viewmodel.CanvasState

@Composable
fun SetStrokeWidthDialogContent(
    state: CanvasState.Drawing,
    modifier: Modifier = Modifier,
    onAction: (CanvasAction) -> Unit = {},
) {
    val drawProperty = state.property

    var currentWidth by remember(drawProperty.width) {
        mutableStateOf(drawProperty.width)
    }
    val range = remember { 1f..50f }

    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)
            .padding(5.dp)
            .padding(horizontal = 16.dp),
    ) {
        StrokeWidthPreview(
            modifier = Modifier
                .padding(vertical = 20.dp),
            color = Color.Red,
            currentStrokeWidth = currentWidth,
        )

        Spacer(modifier = Modifier.height(20.dp))

        StrokeWidthSlider(
            currentStrokeWidth = currentWidth,
            range = range,
            onValueChange = { value ->
                currentWidth = value
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val action = CanvasAction.DrawPropertyManage.SetStrokeWidth(currentWidth)
                onAction(action)
            },
        ) {
            Text(text = stringResource(id = R.string.canvas_dialog_stroke_width_set_button))
        }
    }
}

@Composable
private fun StrokeWidthPreview(
    color: Color,
    currentStrokeWidth: Float,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier.fillMaxWidth()) {
        drawLine(
            color = color,
            strokeWidth = currentStrokeWidth,
            cap = StrokeCap.Round,
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
        )
    }
}

@Composable
private fun StrokeWidthSlider(
    currentStrokeWidth: Float,
    range: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentStrokeWidth by remember(currentStrokeWidth) {
        derivedStateOf {
            currentStrokeWidth.coerceIn(range)
        }
    }

    Slider(
        modifier = modifier,
        value = currentStrokeWidth,
        onValueChange = onValueChange,
        valueRange = range,
        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colorScheme.onSurface,
        ),
    )
}

@Composable
@DefaultPreview
private fun SetStrokeWidthDialogContentPreview() {
    DrawAnimationTheme {
        SetStrokeWidthDialogContent(
            state = CanvasState.Drawing(
                property = DrawProperty.Draw.Line.Brush(
                    color = Color.Red,
                )
            )
        )
    }
}
