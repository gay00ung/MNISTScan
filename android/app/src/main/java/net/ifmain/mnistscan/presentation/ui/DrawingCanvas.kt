package net.ifmain.mnistscan.presentation.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import net.ifmain.mnistscan.util.DrawingCanvasManager

@Composable
fun DrawingCanvas(
    modifier: Modifier = Modifier,
    clearTrigger: Int = 0
) {
    val pathHistory = remember { mutableStateListOf<List<Offset>>() }
    val currentPoints = remember { mutableStateListOf<Offset>() }
    val lastClearTrigger = remember { mutableIntStateOf(0) }
    val strokeWidth = with(LocalDensity.current) { 16.dp.toPx() }
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }

    if (clearTrigger != lastClearTrigger.intValue) {
        lastClearTrigger.intValue = clearTrigger
        pathHistory.clear()
        currentPoints.clear()
        DrawingCanvasManager.clear()
    }

    // 현재 그리고 있는 점들을 실시간으로 업데이트
    LaunchedEffect(currentPoints.toList()) {
        DrawingCanvasManager.updateCurrentPoints(currentPoints.toList())
    }

    Canvas(
        modifier = modifier
            .background(Color.White)
            .onSizeChanged { size ->
                canvasSize = size
                // 실제 캔버스 크기를 DrawingCanvasManager에 전달
                DrawingCanvasManager.setCanvasSize(size.width.toFloat(), size.height.toFloat())
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        currentPoints.clear()
                        currentPoints.add(offset)
                    },
                    onDrag = { change, _ ->
                        currentPoints.add(change.position)
                    },
                    onDragEnd = {
                        if (currentPoints.isNotEmpty()) {
                            pathHistory.add(currentPoints.toList())
                            DrawingCanvasManager.updatePoints(currentPoints.toList())
                            currentPoints.clear()
                        }
                    }
                )
            }
    ) {
        pathHistory.forEach { path ->
            drawPath(
                path = Path().apply {
                    if (path.isNotEmpty()) {
                        moveTo(path[0].x, path[0].y)
                        for (i in 1 until path.size) {
                            lineTo(path[i].x, path[i].y)
                        }
                    }
                },
                color = Color.Black,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth)
            )
        }

        drawPath(
            path = Path().apply {
                if (currentPoints.isNotEmpty()) {
                    moveTo(currentPoints[0].x, currentPoints[0].y)
                    for (i in 1 until currentPoints.size) {
                        lineTo(currentPoints[i].x, currentPoints[i].y)
                    }
                }
            },
            color = Color.Black,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth)
        )
    }
}
