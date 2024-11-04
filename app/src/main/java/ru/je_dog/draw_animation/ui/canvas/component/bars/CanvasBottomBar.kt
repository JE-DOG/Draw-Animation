package ru.je_dog.draw_animation.ui.canvas.component.bars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.je_dog.draw_animation.R
import ru.je_dog.draw_animation.core.compose.preview.DefaultPreview
import ru.je_dog.draw_animation.shared.theme.DrawAnimationTheme
import ru.je_dog.draw_animation.ui.canvas.model.DrawProperty
import ru.je_dog.draw_animation.ui.canvas.viewmodel.CanvasAction
import ru.je_dog.draw_animation.ui.canvas.viewmodel.CanvasState
import ru.je_dog.draw_animation.ui.canvas.viewmodel.dialog.DialogType

@Composable
fun CanvasBottomBar(
    state: CanvasState,
    modifier: Modifier = Modifier,
    onAction: (CanvasAction) -> Unit = {},
) {
    if (state !is CanvasState.Drawing) return
    val drawProperty = state.property

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        IconButton(onClick = {
            val newDrawProperty = DrawProperty.Draw.Line.Pencil()
            val action = CanvasAction.DrawPropertyManage.SetDrawProperty(newDrawProperty)
            onAction(action)
        }) {
            val color = rememberIconColor(isSelected = drawProperty is DrawProperty.Draw.Line.Pencil)

            Icon(
                painter = painterResource(id = R.drawable.ic_pencil),
                contentDescription = null,
                tint = color,
            )
        }
        IconButton(onClick = {
            val newDrawProperty = DrawProperty.Draw.Line.Brush()
            val action = CanvasAction.DrawPropertyManage.SetDrawProperty(newDrawProperty)
            onAction(action)
        }) {
            val color = rememberIconColor(isSelected = drawProperty is DrawProperty.Draw.Line.Brush)

            Icon(
                painter = painterResource(id = R.drawable.ic_brush),
                contentDescription = null,
                tint = color,
            )
        }
        IconButton(onClick = {
            val newDrawProperty = DrawProperty.Eraser()
            val action = CanvasAction.DrawPropertyManage.SetDrawProperty(newDrawProperty)
            onAction(action)
        }) {
            val color = rememberIconColor(isSelected = drawProperty is DrawProperty.Eraser)

            Icon(
                painter = painterResource(id = R.drawable.ic_erase),
                contentDescription = null,
                tint = color,
            )
        }

        if (drawProperty !is DrawProperty.Draw.Line) return@Row
        IconButton(onClick = {
            val dialogType = DialogType.Colors
            val action = CanvasAction.Dialog.ShowDialog(dialogType)
            onAction(action)
        }) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(
                        color = drawProperty.color,
                        shape = CircleShape,
                    ),
            )
        }
    }
}

@Composable
private fun rememberIconColor(isSelected: Boolean): Color {
    val selectedColor = MaterialTheme.colorScheme.primary
    val unselectedColor = MaterialTheme.colorScheme.onBackground
    
    return remember(isSelected) {
        derivedStateOf {
            if (isSelected) {
                selectedColor
            } else {
                unselectedColor
            }
        }
    }.value
}

//region Preview
@Composable
@DefaultPreview
private fun CanvasBottomBarDrawingSelectedPreview() {
    DrawAnimationTheme {
        CanvasBottomBar(
            state = CanvasState.Drawing(),
        )
    }
}

@Composable
@DefaultPreview
private fun CanvasBottomBarDrawingUnselectedPreview() {
    DrawAnimationTheme {
        val drawProperty = DrawProperty.Eraser()
        CanvasBottomBar(
            state = CanvasState.Drawing(
                property = drawProperty,
            ),
        )
    }
}
//endregion
