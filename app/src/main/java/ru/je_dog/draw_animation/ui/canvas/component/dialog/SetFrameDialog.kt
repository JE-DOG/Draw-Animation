package ru.je_dog.draw_animation.ui.canvas.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.je_dog.draw_animation.R
import ru.je_dog.draw_animation.core.compose.preview.DefaultPreview
import ru.je_dog.draw_animation.shared.theme.DrawAnimationTheme
import ru.je_dog.draw_animation.ui.canvas.model.Frame
import ru.je_dog.draw_animation.ui.canvas.viewmodel.CanvasAction
import ru.je_dog.draw_animation.ui.canvas.viewmodel.CanvasState

@Composable
fun SetFrameDialog(
    state: CanvasState.Drawing,
    modifier: Modifier = Modifier,
    onAction: (CanvasAction) -> Unit = {},
) {
    if (!state.showFrames) return

    Dialog(onDismissRequest = {
        val action = CanvasAction.FramesManage.HideAllFrames
        onAction(action)
    }) {
        LazyColumn(
            modifier = modifier
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 10.dp, vertical = 16.dp),
        ) {
            frames(
                frames = state.frames,
                onClick = { frameIndex ->
                    val action = CanvasAction.FramesManage.SetFrame(frameIndex)
                    onAction(action)
                }
            )
        }
    }
}

private fun LazyListScope.frames(
    frames: List<Frame>,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {},
) {
    items(frames.size) { index ->
        FrameItem(
            frameIndex = index,
            modifier = modifier,
            onClick = onClick,
        )
    }
}

@Composable
private fun FrameItem(
    frameIndex: Int,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {},
) {
    Column(
        modifier = modifier
            .clickable(
                onClick = { onClick(frameIndex) }
            )
            .padding(vertical = 10.dp),
    ) {
        Text(
            text = stringResource(id = R.string.canvas_dialog_frame, frameIndex + 1),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        HorizontalDivider()
    }
}

//region Preview
@Composable
@DefaultPreview
private fun FrameItemPreview() {
    DrawAnimationTheme {
        FrameItem(0)
    }
}

@Composable
@DefaultPreview
private fun FrameItemsPreview() {
    val frames = List(10) { Frame() }
    val state = CanvasState.Drawing(
        frames = frames,
        showFrames = true,
    )

    DrawAnimationTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            SetFrameDialog(
                state = state,
            )
        }
    }
}
//endregion


