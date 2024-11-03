package ru.je_dog.draw_animation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import ru.je_dog.draw_animation.shared.theme.DrawAnimationTheme
import ru.je_dog.draw_animation.ui.canvas.CanvasScreen
import ru.je_dog.draw_animation.ui.canvas.viewmodel.CanvasViewModel
import ru.je_dog.draw_animation.ui.canvas.viewmodel.CanvasViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: CanvasViewModel by viewModels {
        CanvasViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DrawAnimationTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    CanvasScreen(
                        viewModel = viewModel,
                        modifier = Modifier
                            .systemBarsPadding(),
                    )
                }
            }
        }
    }
}