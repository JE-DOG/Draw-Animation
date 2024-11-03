package ru.je_dog.draw_animation.ui.canvas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.je_dog.draw_animation.core.ext.collection.popOrNull
import ru.je_dog.draw_animation.ui.canvas.model.Draw
import ru.je_dog.draw_animation.ui.canvas.model.DrawPoint
import ru.je_dog.draw_animation.ui.canvas.model.Frame
import java.util.Stack

class CanvasViewModel : ViewModel() {

    val state: MutableStateFlow<CanvasState> = MutableStateFlow(CanvasState.Drawing())

    private val drawPointsQueue = MutableSharedFlow<DrawPoint>()
    private val savedFramesDraws = hashMapOf<Int, Stack<Draw>>()

    init {
        collectDrawPoints()
    }

    fun action(action: CanvasAction) {
        when(action) {
            is CanvasAction.Drawing -> onDrawingAction(action)
            is CanvasAction.FramesManage -> onFramesManageAction(action)
            is CanvasAction.DrawManage -> onDrawManageAction(action)
        }
    }

    private fun onDrawManageAction(action: CanvasAction.DrawManage) {
        when(action) {
            CanvasAction.DrawManage.Redo -> onDrawRedo()
            CanvasAction.DrawManage.Undo -> onDrawUndo()
        }
    }

    private fun onDrawUndo() {
        val currentState = state.value as? CanvasState.Drawing ?: return
        val frameIndex = currentState.currentFrameIndex
        val drawStack = savedFramesDraws.getOrPut(frameIndex) { Stack() }
        val newFrames = currentState.frames.toMutableList()
        val currentFrame = newFrames.getOrNull(frameIndex) ?: return
        val newDraws = currentFrame.draws.toMutableList()
        val newFrame = currentFrame.copy(draws = newDraws)

        val lastDraw = newDraws.removeLastOrNull() ?: return
        drawStack.add(lastDraw)
        newFrames[frameIndex] = newFrame

        state.update {
            currentState.copy(
                frames = newFrames,
            )
        }
    }

    private fun onDrawRedo() {
        val currentState = state.value as? CanvasState.Drawing ?: return
        val frameIndex = currentState.currentFrameIndex
        val drawStack = savedFramesDraws[frameIndex] ?: return
        val newFrames = currentState.frames.toMutableList()
        val currentFrame = newFrames.getOrNull(frameIndex) ?: return
        val newDraws = currentFrame.draws.toMutableList()
        val newFrame = currentFrame.copy(draws = newDraws)

        val returnedDraw = drawStack.popOrNull() ?: return
        newDraws.add(returnedDraw)
        newFrames[frameIndex] = newFrame

        state.update {
            currentState.copy(
                frames = newFrames,
            )
        }
    }

    private fun onDrawingAction(action: CanvasAction.Drawing) {
        when(action) {
            CanvasAction.Drawing.Ended -> onDrawingEnded()
            CanvasAction.Drawing.Started -> onDrawingStarted()
            is CanvasAction.Drawing.NewPoint -> onDrawingNewPoint(action.point)
        }
    }

    // TODO: Decompose it
    private fun onDrawingStarted() {
        val currentState = state.value as? CanvasState.Drawing ?: return
        val newFrames = currentState.frames.toMutableList()
        val currentFrame = newFrames.getOrNull(currentState.currentFrameIndex) ?: return
        val newDraw = Draw(
            points = emptyList(),
            property = currentState.property,
        )
        val newDraws = currentFrame.draws + newDraw
        newFrames[currentState.currentFrameIndex] = currentFrame.copy(draws = newDraws)
        savedFramesDraws.remove(currentState.currentFrameIndex)

        state.update {
            currentState.copy(
                frames = newFrames,
            )
        }
    }

    private fun onDrawingEnded() {
        val currentState = state.value as? CanvasState.Drawing ?: return
        savedFramesDraws.remove(currentState.currentFrameIndex)
    }

    private fun onDrawingNewPoint(newPoint: DrawPoint) {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            drawPointsQueue.emit(newPoint)
        }
    }

    private fun onFramesManageAction(action: CanvasAction.FramesManage) {
        when(action) {
            CanvasAction.FramesManage.CreateNewFrame -> onCreateNewFrame()
            CanvasAction.FramesManage.DeleteFrame -> onDeleteFrame()
            CanvasAction.FramesManage.ShowAllFrames -> onShowAllFrames()
            CanvasAction.FramesManage.HideAllFrames -> onHideAllFrames()
            is CanvasAction.FramesManage.SetFrame -> onSetFrame(action.frameIndex)
        }
    }

    private fun onSetFrame(frameIndex: Int) {
        val currentState = state.value as? CanvasState.Drawing ?: return
        state.update {
            currentState.copy(
                currentFrameIndex = frameIndex,
                showFrames = false,
            )
        }
    }

    private fun onHideAllFrames() {
        val currentState = state.value as? CanvasState.Drawing ?: return

        state.update {
            currentState.copy(
                showFrames = false,
            )
        }
    }

    private fun onShowAllFrames() {
        val currentState = state.value as? CanvasState.Drawing ?: return

        state.update {
            currentState.copy(
                showFrames = true,
            )
        }
    }

    private fun onCreateNewFrame() {
        val currentState = state.value as? CanvasState.Drawing ?: return
        val newFrames = currentState.frames.toMutableList().apply {
            val newFrame = Frame()
            add(newFrame)
        }

        state.update {
            currentState.copy(
                frames = newFrames,
                currentFrameIndex = newFrames.lastIndex,
            )
        }
    }

    private fun onDeleteFrame() {
        val currentState = state.value as? CanvasState.Drawing ?: return
        var newCurrentFrameIndex = 0
        val newFrames = currentState.frames.toMutableList().apply {
            removeAt(currentState.currentFrameIndex)
            if (isEmpty()) {
                val newFrame = Frame()
                add(newFrame)
            } else {
                newCurrentFrameIndex = currentState.currentFrameIndex + 1
            }
        }

        savedFramesDraws.remove(currentState.currentFrameIndex)

        state.update {
            currentState.copy(
                frames = newFrames,
                currentFrameIndex = newCurrentFrameIndex.coerceIn(0, newFrames.lastIndex),
            )
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
