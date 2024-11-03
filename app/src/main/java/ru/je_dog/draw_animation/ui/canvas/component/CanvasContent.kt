package ru.je_dog.draw_animation.ui.canvas.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.je_dog.draw_animation.ui.canvas.component.bars.CanvasBottomBar
import ru.je_dog.draw_animation.ui.canvas.component.bars.CanvasTopBar
import ru.je_dog.draw_animation.ui.canvas.viewmodel.CanvasAction
import ru.je_dog.draw_animation.ui.canvas.viewmodel.CanvasState

@Composable
fun CanvasContent(
    state: CanvasState,
    modifier: Modifier = Modifier,
    onAction: (CanvasAction) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CanvasTopBar(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                onAction = onAction,
            )
        },
        bottomBar = {
            CanvasBottomBar()
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            CanvasMainContent(
                state = state,
                onAction = onAction,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraLarge)
                    .padding(horizontal = 16.dp),
            )
        }
    }
}
