package ru.je_dog.draw_animation.ui.canvas

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ru.je_dog.draw_animation.ui.canvas.component.CanvasContent
import ru.je_dog.draw_animation.ui.canvas.component.dialog.CanvasDialog
import ru.je_dog.draw_animation.ui.canvas.viewmodel.CanvasState
import ru.je_dog.draw_animation.ui.canvas.viewmodel.CanvasViewModel

@Composable
fun CanvasScreen(
    viewModel: CanvasViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()

    CanvasContent(
        state = state,
        modifier = modifier,
        onAction = viewModel::action,
    )

    CanvasDialog(
        state = state as? CanvasState.Drawing ?: return,
        onAction = viewModel::action,
    )
}
