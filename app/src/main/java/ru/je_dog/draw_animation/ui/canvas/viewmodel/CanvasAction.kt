package ru.je_dog.draw_animation.ui.canvas.viewmodel

import ru.je_dog.draw_animation.ui.canvas.model.DrawPoint

sealed interface CanvasAction {

    sealed interface FramesManage : CanvasAction {

        object CreateNewFrame : FramesManage

        object DeleteFrame : FramesManage

        object ShowAllFrames : FramesManage

        object HideAllFrames : FramesManage

        class SetFrame(val frameIndex: Int) : FramesManage
    }

    sealed interface Drawing : CanvasAction {

        data class NewPoint(
            val point: DrawPoint,
        ) : Drawing

        object Ended : Drawing
    }
}
