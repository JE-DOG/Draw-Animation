package ru.je_dog.draw_animation.ui.canvas.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
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
fun SetAnimationSpeed(
    modifier: Modifier = Modifier,
    onAction: (CanvasAction) -> Unit = {},
) {
    var animationSpeed by remember {
        mutableLongStateOf(150L)
    }

    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)
            .padding(5.dp)
            .padding(horizontal = 16.dp),
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = animationSpeed.toString(),
            onValueChange = { value ->
                val newSpeed = value.toLongOrNull() ?: return@TextField
                animationSpeed = newSpeed
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val action = CanvasAction.Animation.SetAnimationSpeed(animationSpeed)
                onAction(action)
            }
        ) {
            Text(text = stringResource(id = R.string.canvas_dialog_animation_speed_confirm_button))
        }
    }
}

@Composable
@DefaultPreview
private fun SetAnimationSpeedPreview() {
    DrawAnimationTheme {
        SetAnimationSpeed()
    }
}
