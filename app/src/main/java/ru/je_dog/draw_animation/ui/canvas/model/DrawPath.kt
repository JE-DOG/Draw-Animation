package ru.je_dog.draw_animation.ui.canvas.model

import androidx.compose.ui.graphics.Path

data class DrawPath(
    val path: Path = Path(),
    val property: DrawProperty = DrawProperty.Draw.Line.Pencil(),
)
