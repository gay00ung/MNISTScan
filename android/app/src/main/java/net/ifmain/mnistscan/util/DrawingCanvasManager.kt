package net.ifmain.mnistscan.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.core.graphics.createBitmap

object DrawingCanvasManager {
    private val allPaths: MutableList<List<Offset>> = mutableListOf()
    private var currentPath: List<Offset> = emptyList()
    private var canvasWidth: Float = 280f
    private var canvasHeight: Float = 280f

    fun updatePoints(points: List<Offset>) {
        allPaths.add(points)
        Log.d("DrawingCanvasManager", "Added path with ${points.size} points")
    }

    fun updateCurrentPoints(points: List<Offset>) {
        currentPath = points
    }

    fun setCanvasSize(width: Float, height: Float) {
        canvasWidth = width
        canvasHeight = height
        Log.d("DrawingCanvasManager", "Canvas size set to: ${width}x${height}")
    }

    fun getBitmap(): Bitmap {
        Log.d("DrawingCanvasManager", "Creating bitmap: canvas=${canvasWidth}x${canvasHeight}")
        Log.d("DrawingCanvasManager", "Total paths: ${allPaths.size}, current points: ${currentPath.size}")

        // 캔버스 크기 그대로 비트맵 생성
        val bitmap = createBitmap(canvasWidth.toInt(), canvasHeight.toInt())
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.BLACK)

        val paint = Paint().apply {
            color = Color.WHITE
            strokeWidth = 16f
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
            isAntiAlias = true
        }

        allPaths.forEach { path ->
            drawPath(canvas, path, paint)
        }

        if (currentPath.isNotEmpty()) {
            drawPath(canvas, currentPath, paint)
        }

        return bitmap
    }

    private fun drawPath(canvas: Canvas, path: List<Offset>, paint: Paint) {
        if (path.isEmpty()) return
        
        if (path.size == 1) {
            val originalStyle = paint.style
            paint.style = Paint.Style.FILL
            canvas.drawCircle(
                path[0].x,
                path[0].y,
                8f,
                paint
            )
            paint.style = originalStyle
        } else {
            var prev = path[0]
            for (i in 1 until path.size) {
                val curr = path[i]
                canvas.drawLine(
                    prev.x, 
                    prev.y, 
                    curr.x, 
                    curr.y, 
                    paint
                )
                prev = curr
            }
        }
    }

    fun clear() {
        allPaths.clear()
        currentPath = emptyList()
        Log.d("DrawingCanvasManager", "Cleared all paths")
    }
}
