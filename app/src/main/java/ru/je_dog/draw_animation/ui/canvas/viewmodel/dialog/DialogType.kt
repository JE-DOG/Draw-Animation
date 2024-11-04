package ru.je_dog.draw_animation.ui.canvas.viewmodel.dialog

sealed class DialogType {

    object CreateNewFrame: DialogType()

    object AnimationSpeed: DialogType()

    object StrokeWidth: DialogType()

    object Colors : DialogType()

    object Frames : DialogType()
}
