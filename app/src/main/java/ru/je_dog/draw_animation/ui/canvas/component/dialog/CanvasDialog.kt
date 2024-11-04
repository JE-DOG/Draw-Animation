package ru.je_dog.draw_animation.ui.canvas.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            DialogType.ShowColors -> {
                SetColorDialogContent(
                    onAction = onAction,
                )
            }
            DialogType.ShowFrames -> {
                SetFrameDialogContent(
                    frames = state.frames,
                    onAction = onAction,
                )
            }
        }
    }
}
