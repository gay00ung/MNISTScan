package net.ifmain.mnistscan.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.compose.ui.geometry.Offset
import androidx.core.graphics.createBitmap

object DrawingCanvasManager {
    private var lastDrawnPoints: List<Offset> = emptyList()

    fun updatePoints(points: List<Offset>) {
        lastDrawnPoints = points
    }

    fun getBitmap(): Bitmap {
        val bitmapSize = 28
        val bitmap = createBitmap(bitmapSize, bitmapSize)
        val canvas = Canvas(bitmap)
        val paint = Paint().apply {
            color = Color.BLACK
            strokeWidth = 2f
            style = Paint.Style.STROKE
            isAntiAlias = true
        }

        if (lastDrawnPoints.isNotEmpty()) {
            var prev = lastDrawnPoints[0]
            for (i in 1 until lastDrawnPoints.size) {
                val curr = lastDrawnPoints[i]
                canvas.drawLine(prev.x, prev.y, curr.x, curr.y, paint)
                prev = curr
            }
        }

        return bitmap
    }

    fun clear() {
        lastDrawnPoints = emptyList()
    }
}
