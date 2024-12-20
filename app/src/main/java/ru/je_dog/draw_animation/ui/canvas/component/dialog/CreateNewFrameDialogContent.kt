package ru.je_dog.draw_animation.ui.canvas.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.je_dog.draw_animation.R
import ru.je_dog.draw_animation.core.compose.preview.DefaultPreview
import ru.je_dog.draw_animation.shared.theme.DrawAnimationTheme
import ru.je_dog.draw_animation.ui.canvas.viewmodel.CanvasAction

@Composable
fun CreateNewFrameDialogContent(
    modifier: Modifier = Modifier,
    onAction: (CanvasAction) -> Unit = {},
) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 10.dp, vertical = 16.dp),
    ) {
        CreateEmptyFrameItem(
            onClick = {
                val action = CanvasAction.FramesManage.CreateNewFrame
                onAction(action)
            }
        )
        CreateCopyFrameItem(
            onClick = {
                val action = CanvasAction.FramesManage.CreateNewFrameByCopy
                onAction(action)
            }
        )
        CreateRandomFramesItem(
            onClick = { count ->
                val action = CanvasAction.FramesManage.CreateRandomFrames(count)
                onAction(action)
            }
        )
    }
}

@Composable
private fun CreateEmptyFrameItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .clickable(
                onClick = onClick
            )
            .padding(vertical = 10.dp),
    ) {
        Text(
            text = stringResource(id = R.string.canvas_dialog_create_frame_empty),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        HorizontalDivider()
    }
}

@Composable
private fun CreateCopyFrameItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .clickable(
                onClick = onClick
            )
            .padding(vertical = 10.dp),
    ) {
        Text(
            text = stringResource(id = R.string.canvas_dialog_create_frame_copy),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        HorizontalDivider()
    }
}

@Composable
private fun CreateRandomFramesItem(
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
) {
    var count by remember {
        mutableIntStateOf(1)
    }

    Column(
        modifier = modifier
            .clickable(
                onClick = { onClick(count) }
            )
            .padding(vertical = 10.dp),
    ) {
        Row {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.canvas_dialog_create_random_frames),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.width(10.dp))

            TextField(
                modifier = Modifier.weight(0.3f),
                value = count.toString(),
                onValueChange = { value ->
                    val newCount = value.toIntOrNull() ?: 0
                    count = newCount
                }
            )
        }
        HorizontalDivider()
    }
}

@Composable
@DefaultPreview
private fun CreateNewFrameDialogContentPreview() {
    DrawAnimationTheme {
        CreateNewFrameDialogContent()
    }
}
