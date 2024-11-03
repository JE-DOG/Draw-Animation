package ru.je_dog.draw_animation.core.ext.canvas

import androidx.compose.ui.geometry.Offset
import ru.je_dog.draw_animation.ui.canvas.model.DrawPoint

fun Offset.toDrawPoint() = DrawPoint(x, y)
