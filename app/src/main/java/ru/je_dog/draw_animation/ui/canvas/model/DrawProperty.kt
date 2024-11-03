package ru.je_dog.draw_animation.ui.canvas.model

import androidx.compose.ui.graphics.Color

sealed class DrawProperty {

    // TODO
    data class Eraser(
        val width: Float,
    ) : DrawProperty()

    data class Line(
        val color: Color = Color.Red,
        val width: Float = 5f,
    ) : DrawProperty()
}
