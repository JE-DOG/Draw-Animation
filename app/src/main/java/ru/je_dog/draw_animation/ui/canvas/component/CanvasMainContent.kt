package ru.je_dog.draw_animation.ui.canvas.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
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
    Box(
        modifier = modifier,
    ) {
        BackgroundImage(
            modifier = Modifier.fillMaxWidth(),
        )

        Canvas(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                .pointerInput(Unit) {
                    if (state !is CanvasState.Drawing) return@pointerInput

                    detectDragGestures(
                        onDragStart = {
                            val action = CanvasAction.Drawing.Started
                            onAction(action)
                        },
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
                },
        ) {
            val frame = state.frames[state.currentFrameIndex]
            val drawPaths = DrawHelper.generateDrawPaths(frame.draws)
            val previousFrame = if (state is CanvasState.Drawing) {
                state.frames.getOrNull(state.currentFrameIndex - 1)
            } else {
                null
            }
            val previousFrameDrawPaths = if (state is CanvasState.Drawing) {
                DrawHelper.generateDrawPathsForPreviousFrame(previousFrame?.draws ?: emptyList())
            } else {
                null
            }

            previousFrameDrawPaths?.fastForEach { drawPath ->
                drawPath.property.draw(
                    path = drawPath.path,
                    scope = this,
                )
            }

            drawPaths.fastForEach { drawPath ->
                drawPath.property.draw(
                    path = drawPath.path,
                    scope = this,
                )
            }
        }
    }
}

@Composable
private fun BackgroundImage(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.img_canvas),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
        )
    }
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
