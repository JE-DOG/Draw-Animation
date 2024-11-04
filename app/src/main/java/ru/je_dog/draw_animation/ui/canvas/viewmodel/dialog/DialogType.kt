package ru.je_dog.draw_animation.ui.canvas.viewmodel.dialog

sealed class DialogType {

    object StrokeWidth: DialogType()

    object Colors : DialogType()

    object Frames : DialogType()
}
