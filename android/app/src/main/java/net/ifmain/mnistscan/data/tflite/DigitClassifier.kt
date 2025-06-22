package net.ifmain.mnistscan.data.tflite

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import org.tensorflow.lite.Interpreter
import androidx.core.graphics.get
import androidx.core.graphics.scale

class DigitClassifier(context: Context) {

    private val interpreter: Interpreter

    init {
        val model = TFLiteModelLoader.loadModel(context, "mnist.tflite")
        interpreter = Interpreter(model)
    }

    fun classify(bitmap: Bitmap): Int {
        val input = convertBitmapToInputArray(bitmap) // 전처리
        val output = Array(1) { FloatArray(10) }
        interpreter.run(input, output)

        return output[0].indices.maxBy { output[0][it] }
    }

    private fun convertBitmapToInputArray(bitmap: Bitmap): Array<Array<FloatArray>> {
        // 28x28로 리사이징
        val resized = bitmap.scale(28, 28)

        // 2차원 float 배열 생성
        val result = Array(1) { Array(28) { FloatArray(28) } }

        // 각 픽셀을 순회하며 grayscale 값 추출
        for (y in 0 until 28) {
            for (x in 0 until 28) {
                val pixel = resized[x, y]

                val r = Color.red(pixel)
                val g = Color.green(pixel)
                val b = Color.blue(pixel)

                // 평균 또는 luminance 기반 grayscale 변환
                val gray = (r + g + b) / 3.0f / 255.0f // 0~1로 정규화

                result[0][y][x] = gray
            }
        }
        return result
    }
}
