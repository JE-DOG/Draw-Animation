package ru.je_dog.draw_animation.ui.canvas.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import ru.je_dog.draw_animation.ui.canvas.viewmodel.CanvasAction
import ru.je_dog.draw_animation.ui.canvas.viewmodel.CanvasState
import ru.je_dog.draw_animation.ui.canvas.viewmodel.dialog.DialogType

@Composable
fun CanvasDialog(
    state: CanvasState.Drawing,
    onAction: (CanvasAction) -> Unit = {},
) {
    if (state.dialogType == null) return

    Dialog(onDismissRequest = {
        val action = CanvasAction.Dialog.HideDialog
        onAction(action)
    }) {
        when(state.dialogType) {
            DialogType.Colors -> {
                SetColorDialogContent(
                    onAction = onAction,
                )
            }

            DialogType.Frames -> {
                SetFrameDialogContent(
                    frames = state.frames,
                    onAction = onAction,
                )
            }

            DialogType.StrokeWidth -> {
                SetStrokeWidthDialogContent(
                    state = state,
                    onAction = onAction,
                )
            }

            DialogType.AnimationSpeed -> {
                SetAnimationSpeed(
                    onAction = onAction,
                )
            }

            DialogType.CreateNewFrame -> {
                CreateNewFrameDialogContent(
                    onAction = onAction,
                )
            }
        }
    }
}
