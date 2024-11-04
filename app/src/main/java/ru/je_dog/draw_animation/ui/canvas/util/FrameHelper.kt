package ru.je_dog.draw_animation.ui.canvas.util

import ru.je_dog.draw_animation.ui.canvas.model.Draw
import ru.je_dog.draw_animation.ui.canvas.model.DrawPoint
import ru.je_dog.draw_animation.ui.canvas.model.DrawProperty
import ru.je_dog.draw_animation.ui.canvas.model.Frame
import kotlin.random.Random

object FrameHelper {

    fun createRandomFrame(): Frame {
        val countDraws = Random.nextInt(3, 10)
        val draws = List(countDraws) {
            val points = List(countDraws) {
                val x = Random.nextInt(200, 1000).toFloat()
                val y = Random.nextInt(200, 1000).toFloat()
                DrawPoint(
                    x = y,
                    y = x,
                )
            }

            Draw(
                points,
                DrawProperty.Draw.Line.Pencil()
            )
        }

        return Frame(
            draws = draws
        )
    }
}