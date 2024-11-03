package ru.je_dog.draw_animation.ui.canvas.viewmodel

import ru.je_dog.draw_animation.ui.canvas.model.DrawProperty
import ru.je_dog.draw_animation.ui.canvas.model.Frame

sealed class CanvasState {

    abstract val frames: List<Frame>
    abstract val currentFrameIndex: Int

    // TODO
    data class ShowAnimation(
        override val frames: List<Frame> = listOf(Frame()),
        override val currentFrameIndex: Int = 0,
    ) : CanvasState()

    data class Drawing(
        override val frames: List<Frame> = listOf(Frame()),
        override val currentFrameIndex: Int = 0,
        val property: DrawProperty = DrawProperty.Line(),
        val showFrames: Boolean = false,
    ) : CanvasState()
}
