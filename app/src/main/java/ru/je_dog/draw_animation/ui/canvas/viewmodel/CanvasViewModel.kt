package ru.je_dog.draw_animation.ui.canvas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.je_dog.draw_animation.ui.canvas.model.Draw
import ru.je_dog.draw_animation.ui.canvas.model.DrawPoint

class CanvasViewModel : ViewModel() {

    val state: MutableStateFlow<CanvasState> = MutableStateFlow(CanvasState.Drawing())

    private val drawPointsQueue = MutableSharedFlow<DrawPoint>(extraBufferCapacity = 10)

    init {
        collectDrawPoints()
    }

    fun action(action: CanvasAction) {
        when(action) {
            is CanvasAction.Drawing -> onDrawingAction(action)
        }
    }

    private fun onDrawingAction(action: CanvasAction.Drawing) {
        when(action) {
            CanvasAction.Drawing.Ended -> onDrawingEnded()
            is CanvasAction.Drawing.NewPoint -> onDrawingNewPoint(action.point)
        }
    }

    // TODO: Decompose it
    private fun onDrawingEnded() {
        val currentState = state.value as? CanvasState.Drawing ?: return
        val newFrames = currentState.frames.toMutableList()
        val currentFrame = newFrames.getOrNull(currentState.currentFrameIndex) ?: return
        val newDraw = Draw(
            points = emptyList(),
            property = currentState.property,
        )
        val newDraws = currentFrame.draws + newDraw
        newFrames[currentState.currentFrameIndex] = currentFrame.copy(draws = newDraws)

        state.update {
            currentState.copy(
                frames = newFrames,
            )
        }
    }

    private fun onDrawingNewPoint(newPoint: DrawPoint) {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            drawPointsQueue.emit(newPoint)
        }
    }

    // TODO: Decompose it
    private fun collectDrawPoints() {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            drawPointsQueue.collect { newPoint ->
                val currentState = state.value as? CanvasState.Drawing ?: return@collect
                val newFrames = currentState.frames.toMutableList()
                val currentFrame = newFrames.getOrNull(currentState.currentFrameIndex) ?: return@collect
                val newDraws = currentFrame.draws.toMutableList()
                if (newDraws.isEmpty()) {
                    val defaultDraw = Draw(emptyList(), currentState.property)
                    newDraws.add(defaultDraw)
                }
                val lastDraw = newDraws.lastOrNull() ?: return@collect
                val newPoints = lastDraw.points + newPoint
                val newDraw = lastDraw.copy(
                    points = newPoints,
                    property = currentState.property,
                )
                newDraws[newDraws.lastIndex] = newDraw
                newFrames[currentState.currentFrameIndex] = currentFrame.copy(draws = newDraws)

                state.update {
                    currentState.copy(
                        frames = newFrames,
                    )
                }
            }
        }
    }
}
