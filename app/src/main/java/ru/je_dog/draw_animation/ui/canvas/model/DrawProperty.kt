package ru.je_dog.draw_animation.ui.canvas.model

import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke

sealed class DrawProperty {

    abstract fun draw(
        path: Path,
        scope: DrawScope,
    )

    data class Eraser(
        val width: Float = 5f,
    ) : DrawProperty() {
        override fun draw(path: Path, scope: DrawScope) = with(scope) {
            drawPath(
                path = path,
                color = Color.Transparent,
                style = Stroke(
                    width = width,
                ),
                blendMode = BlendMode.Clear,
            )
        }
    }

    sealed class Draw : DrawProperty() {

        abstract val alpha: Float

        abstract fun copyProperty(alpha: Float = this.alpha): Draw

        sealed class Line : Draw() {

            abstract val color: Color
            abstract val width: Float

            abstract fun copyProperty(
                alpha: Float = this.alpha,
                color: Color = this.color,
                width: Float = this.width,
            ): Draw

            data class Pencil(
                override val color: Color = Color.Red,
                override val width: Float = 5f,
                override val alpha: Float = 1.0f,
            ) : Line() {
                override fun draw(path: Path, scope: DrawScope) = with(scope) {
                    drawPath(
                        path = path,
                        color = color,
                        style = Stroke(
                            width = width,
                            cap = StrokeCap.Butt,
                        ),
                        alpha = alpha,
                    )
                }

                override fun copyProperty(alpha: Float): Pencil {
                    return copy(
                        alpha = alpha,
                    )
                }

                override fun copyProperty(
                    alpha: Float,
                    color: Color,
                    width: Float,
                ): Draw {
                    return copy(
                        alpha = alpha,
                        color = color,
                        width = width,
                    )
                }
            }

            data class Brush(
                override val color: Color = Color.Red,
                override val width: Float = 5f,
                override val alpha: Float = 1.0f,
            ) : Line() {
                override fun draw(path: Path, scope: DrawScope) = with(scope) {
                    drawPath(
                        path = path,
                        color = color,
                        style = Stroke(
                            width = width,
                            cap = StrokeCap.Round,
                        ),
                        alpha = alpha,
                    )
                }

                override fun copyProperty(alpha: Float): Brush {
                    return this.copy(
                        alpha = alpha,
                        color = color,
                    )
                }

                override fun copyProperty(
                    alpha: Float,
                    color: Color,
                    width: Float,
                ): Draw {
                    return copy(
                        alpha = alpha,
                        color = color,
                        width = width,
                    )
                }
            }
        }
    }
}
