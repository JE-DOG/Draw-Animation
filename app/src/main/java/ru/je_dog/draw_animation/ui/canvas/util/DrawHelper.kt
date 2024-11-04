package ru.je_dog.draw_animation.ui.canvas.util

import androidx.compose.ui.graphics.Path
import ru.je_dog.draw_animation.core.ext.canvas.lineTo
import ru.je_dog.draw_animation.core.ext.canvas.moveTo
import ru.je_dog.draw_animation.ui.canvas.model.Draw
import ru.je_dog.draw_animation.ui.canvas.model.DrawPath
import ru.je_dog.draw_animation.ui.canvas.model.DrawProperty

object DrawHelper {

    fun generateDrawPathsForPreviousFrame(draws: List<Draw>): List<DrawPath> {
        return draws.map(::generateDrawPathForPreviousFrame)
    }

    fun generateDrawPaths(draws: List<Draw>): List<DrawPath> {
        return draws.map(::generateDrawPath)
    }

    private fun generateDrawPath(draw: Draw): DrawPath = with(draw) {
        val path = Path()
        if (points.size < 2) return DrawPath(path, property)
        val firstDrawPoint = points.first()
        path.moveTo(firstDrawPoint)

        for (i in 1..points.lastIndex) {
            val pointF = points[i]
            path.lineTo(pointF)
        }

        return DrawPath(
            path = path,
            property = property,
        )
    }

    private fun generateDrawPathForPreviousFrame(draw: Draw): DrawPath = with(draw) {
        val path = Path()
        val newProperty = if (property is DrawProperty.Draw) {
            property.copy(
                alpha = PREVIOUS_DRAW_ALPHA,
            )
        } else {
            property
        }

        if (points.size < 2) return DrawPath(path, property)
        val firstDrawPoint = points.first()
        path.moveTo(firstDrawPoint)

        for (i in 1..points.lastIndex) {
            val pointF = points[i]
            path.lineTo(pointF)
        }

        return DrawPath(
            path = path,
            property = newProperty,
        )
    }

    private const val PREVIOUS_DRAW_ALPHA = 0.3F
}
