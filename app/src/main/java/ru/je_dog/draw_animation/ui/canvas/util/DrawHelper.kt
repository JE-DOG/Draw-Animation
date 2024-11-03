package ru.je_dog.draw_animation.ui.canvas.util

import androidx.compose.ui.graphics.Path
import ru.je_dog.draw_animation.core.ext.canvas.lineTo
import ru.je_dog.draw_animation.core.ext.canvas.moveTo
import ru.je_dog.draw_animation.ui.canvas.model.Draw

object DrawHelper {

    fun generatePaths(draws: List<Draw>): List<Path> {
        return draws.map(::generatePath)
    }

    private fun generatePath(draw: Draw): Path = with(draw) {
        val path = Path()
        if (points.size < 2) return path
        val firstDrawPoint = points.first()
        path.moveTo(firstDrawPoint)

        for (i in 1..points.lastIndex) {
            val pointF = points[i]
            path.lineTo(pointF)
        }

        return path
    }
}
