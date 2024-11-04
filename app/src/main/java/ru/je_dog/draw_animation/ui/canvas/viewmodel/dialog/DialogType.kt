package ru.je_dog.draw_animation.ui.canvas.viewmodel.dialog

sealed class DialogType {

    object ShowColors : DialogType()

    object ShowFrames : DialogType()
}
