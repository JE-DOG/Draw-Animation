package ru.je_dog.draw_animation.ui.canvas.viewmodel

import ru.je_dog.draw_animation.ui.canvas.model.DrawPoint

sealed interface CanvasAction {

    sealed interface Drawing : CanvasAction {

        data class NewPoint(
            val point: DrawPoint,
        ) : Drawing

        data object Ended : Drawing
    }
}
