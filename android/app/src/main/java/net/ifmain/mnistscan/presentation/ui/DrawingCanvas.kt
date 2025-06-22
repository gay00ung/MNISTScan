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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import net.ifmain.mnistscan.util.DrawingCanvasManager

@Composable
fun DrawingCanvas(
    modifier: Modifier = Modifier,
    clearTrigger: Boolean = false
) {
    val path = remember { Path() }
    val points = remember { mutableStateListOf<Offset>() }

    val strokeWidth = with(LocalDensity.current) { 16.dp.toPx() }

    LaunchedEffect(clearTrigger) {
        if (clearTrigger) {
            points.clear()
        }
    }

    Canvas(
        modifier = modifier
            .background(Color.White)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset -> points.add(offset) },
                    onDrag = { _, offset -> points.add(offset) },
                    onDragEnd = {
                        DrawingCanvasManager.updatePoints(points.toList())
                        points.clear()
                    }
                )
            }
    ) {
        drawPath(
            path = Path().apply {
                if (points.isNotEmpty()) {
                    moveTo(points[0].x, points[0].y)
                    for (i in 1 until points.size) {
                        lineTo(points[i].x, points[i].y)
                    }
                }
            },
            color = Color.Black,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth)
        )
    }
}
