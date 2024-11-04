package ru.je_dog.draw_animation.ui.canvas.viewmodel

import ru.je_dog.draw_animation.ui.canvas.model.DrawProperty
import ru.je_dog.draw_animation.ui.canvas.model.Frame
import ru.je_dog.draw_animation.ui.canvas.viewmodel.dialog.DialogType

sealed class CanvasState {

    abstract val frames: List<Frame>
    abstract val currentFrameIndex: Int

    data class ShowAnimation(
        override val frames: List<Frame> = listOf(Frame()),
        override val currentFrameIndex: Int = 0,
    ) : CanvasState()

    data class Drawing(
        override val frames: List<Frame> = listOf(Frame()),
        override val currentFrameIndex: Int = 0,
        val property: DrawProperty = DrawProperty.Draw.Line.Pencil(),
        val dialogType: DialogType? = null,
    ) : CanvasState()
}
