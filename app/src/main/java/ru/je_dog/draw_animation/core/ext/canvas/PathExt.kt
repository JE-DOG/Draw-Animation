package ru.je_dog.draw_animation.core.ext.canvas

import androidx.compose.ui.graphics.Path
import ru.je_dog.draw_animation.ui.canvas.model.DrawPoint

fun Path.moveTo(drawPoint: DrawPoint) = moveTo(drawPoint.x, drawPoint.y)
fun Path.lineTo(drawPoint: DrawPoint) = lineTo(drawPoint.x, drawPoint.y)
