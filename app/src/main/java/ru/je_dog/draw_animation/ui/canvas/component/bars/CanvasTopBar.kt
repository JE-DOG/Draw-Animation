package ru.je_dog.draw_animation.ui.canvas.component.bars

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import ru.je_dog.draw_animation.R
import ru.je_dog.draw_animation.core.compose.preview.DefaultPreview
import ru.je_dog.draw_animation.shared.theme.DrawAnimationTheme
import ru.je_dog.draw_animation.ui.canvas.viewmodel.CanvasAction
import ru.je_dog.draw_animation.ui.canvas.viewmodel.dialog.DialogType

@Composable
fun CanvasTopBar(
    modifier: Modifier = Modifier,
    onAction: (CanvasAction) -> Unit = {},
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DrawChangeManage(
            onAction = onAction,
        )

        LayersManage(
            onAction = onAction,
        )

        AnimationManage(
            onAction = onAction,
        )
    }
}

@Composable
private fun DrawChangeManage(
    modifier: Modifier = Modifier,
    onAction: (CanvasAction) -> Unit = {},
) {
    Row(
        modifier = modifier,
    ) {
        IconButton(onClick = {
            val action = CanvasAction.DrawManage.Undo
            onAction(action)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_undo),
                tint = MaterialTheme.colorScheme.outline,
                contentDescription = null,
            )
        }

        IconButton(onClick = {
            val action = CanvasAction.DrawManage.Redo
            onAction(action)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_redo),
                tint = MaterialTheme.colorScheme.outline,
                contentDescription = null,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LayersManage(
    modifier: Modifier = Modifier,
    onAction: (CanvasAction) -> Unit = {},
) {
    Row(
        modifier = modifier,
    ) {
        IconButton(onClick = {}) {
            Icon(
                modifier = Modifier
                    .combinedClickable(
                        true,
                        onClick = {
                            val action = CanvasAction.FramesManage.DeleteFrame
                            onAction(action)
                        },
                        onLongClick = {
                            val action = CanvasAction.FramesManage.DeleteAllFrames
                            onAction(action)
                        }
                    ),
                painter = painterResource(id = R.drawable.ic_bin),
                tint = MaterialTheme.colorScheme.outline,
                contentDescription = null,
            )
        }

        IconButton(onClick = {
            val dialogType = DialogType.CreateNewFrame
            val action = CanvasAction.Dialog.ShowDialog(dialogType)
            onAction(action)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_create_frame),
                tint = MaterialTheme.colorScheme.outline,
                contentDescription = null,
            )
        }

        IconButton(onClick = {
            val dialogType = DialogType.Frames
            val action = CanvasAction.Dialog.ShowDialog(dialogType)
            onAction(action)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_layers),
                tint = MaterialTheme.colorScheme.outline,
                contentDescription = null,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AnimationManage(
    modifier: Modifier = Modifier,
    onAction: (CanvasAction) -> Unit = {},
) {
    Row(
        modifier = modifier,
    ) {
        IconButton(onClick = {
            val action = CanvasAction.Animation.Stop
            onAction(action)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_pause),
                tint = MaterialTheme.colorScheme.outline,
                contentDescription = null,
            )
        }

        IconButton(
            onClick = {}
        ) {
            Icon(
                modifier = Modifier
                    .combinedClickable(
                        true,
                        onLongClick = {
                            val dialogType = DialogType.AnimationSpeed
                            val action = CanvasAction.Dialog.ShowDialog(dialogType)
                            onAction(action)
                        },
                        onClick = {
                            val action = CanvasAction.Animation.Start
                            onAction(action)
                        }
                    ),
                painter = painterResource(id = R.drawable.ic_play),
                tint = MaterialTheme.colorScheme.outline,
                contentDescription = null,
            )
        }
    }
}

//region Preview
@Composable
@DefaultPreview
private fun CanvasTopBarPreview() {
    DrawAnimationTheme {
        CanvasTopBar(
            modifier = Modifier.fillMaxWidth()
        )
    }
}
//endregion
