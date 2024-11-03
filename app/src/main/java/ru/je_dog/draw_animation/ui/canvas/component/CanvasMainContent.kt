package ru.je_dog.draw_animation.ui.canvas.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.util.fastForEach
import ru.je_dog.draw_animation.R
import ru.je_dog.draw_animation.core.ext.canvas.toDrawPoint
import ru.je_dog.draw_animation.shared.theme.DrawAnimationTheme
import ru.je_dog.draw_animation.ui.canvas.util.DrawHelper
import ru.je_dog.draw_animation.ui.canvas.viewmodel.CanvasAction
import ru.je_dog.draw_animation.ui.canvas.viewmodel.CanvasState

@Composable
fun CanvasMainContent(
    state: CanvasState,
    modifier: Modifier = Modifier,
    onAction: (CanvasAction) -> Unit = {},
) {
    BackgroundImage(
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                if (state !is CanvasState.Drawing) return@pointerInput

                detectDragGestures(
                    onDragEnd = {
                        val action = CanvasAction.Drawing.Ended
                        onAction(action)
                    },
                ) { change, _ ->
                    change.consume()
                    val drawPoint = change.position.toDrawPoint()
                    val action = CanvasAction.Drawing.NewPoint(drawPoint)
                    onAction(action)
                }
            }
            .drawWithCache {
                val stroke = Stroke(
                    width = 10f,
                )
                val frame = state.frames[state.currentFrameIndex]
                val paths = DrawHelper.generatePaths(frame.draws)

                onDrawWithContent {
                    drawContent()

                    paths.fastForEach { path ->
                        drawPath(
                            path = path,
                            style = stroke,
                            color = Color.Red,
                        )
                    }
                }
            },
    )
}

@Composable
private fun BackgroundImage(
    modifier: Modifier = Modifier,
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.img_canvas),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
    )
}

//region Preview
@Composable
@Preview
private fun CanvasMainContentPreview() {
    DrawAnimationTheme {
        CanvasMainContent(
            state = CanvasState.Drawing(),
        )
    }
}
//endregion
