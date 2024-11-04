package ru.je_dog.draw_animation.ui.canvas.viewmodel

import androidx.compose.ui.graphics.Color
import ru.je_dog.draw_animation.ui.canvas.model.DrawPoint
import ru.je_dog.draw_animation.ui.canvas.model.DrawProperty
import ru.je_dog.draw_animation.ui.canvas.viewmodel.dialog.DialogType

sealed interface CanvasAction {

    sealed interface Dialog : CanvasAction {

        data class ShowDialog(val dialogType: DialogType) : Dialog

        object HideDialog : Dialog
    }

    sealed interface DrawPropertyManage : CanvasAction {

        data class SetDrawProperty(
            val drawProperty: DrawProperty,
        ) : DrawPropertyManage

        data class SetStrokeWidth(
            val width: Float,
        ) : DrawPropertyManage

        data class SetColor(
            val color: Color,
        ) : DrawPropertyManage
    }

    sealed interface Animation : CanvasAction {

        object Start : Animation

        object Stop : Animation

        data class SetAnimationSpeed(val speed: Long) : Animation
    }

    sealed interface FramesManage : CanvasAction {

        object CreateNewFrameByCopy : FramesManage

        object CreateNewFrame : FramesManage

        object DeleteFrame : FramesManage

        object DeleteAllFrames : FramesManage

        class SetFrame(val frameIndex: Int) : FramesManage
    }

    sealed interface DrawManage : CanvasAction {

        object Undo : DrawManage

        object Redo : DrawManage
    }

    sealed interface Drawing : CanvasAction {

        data class NewPoint(
            val point: DrawPoint,
        ) : Drawing

        object Ended : Drawing

        object Started : Drawing
    }
}
