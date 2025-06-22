package net.ifmain.mnistscan.data.tflite

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import org.tensorflow.lite.Interpreter
import androidx.core.graphics.get
import androidx.core.graphics.scale
import javax.inject.Inject
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set

class DigitClassifier @Inject constructor(
    context: Context
) {
    private val interpreter: Interpreter

    init {
        val model = TFLiteModelLoader.loadModel(context, "mnist.tflite")
        interpreter = Interpreter(model)
    }

    fun classify(bitmap: Bitmap): Int {
        Log.d("DigitClassifier", "Input bitmap: ${bitmap.width}x${bitmap.height}")
        val input = convertBitmapToInputArray(bitmap) // 전처리
        val output = Array(1) { FloatArray(10) }
        interpreter.run(input, output)

        val result = output[0].indices.maxBy { output[0][it] }
        Log.d("DigitClassifier", "Prediction: $result, Confidence: ${output[0][result]}")
        Log.d("DigitClassifier", "All outputs: ${output[0].contentToString()}")
        
        return result
    }

    private fun convertBitmapToInputArray(bitmap: Bitmap): Array<Array<FloatArray>> {
        // 이미 28x28이므로 리사이징 불필요, 필요시에만 수행
        val resized = if (bitmap.width != 28 || bitmap.height != 28) {
            bitmap.scale(28, 28)
        } else {
            bitmap
        }

        // 2차원 float 배열 생성
        val result = Array(1) { Array(28) { FloatArray(28) } }

        // 각 픽셀을 순회하며 grayscale 값 추출
        var pixelCount = 0
        var whitePixels = 0
        
        for (y in 0 until 28) {
            for (x in 0 until 28) {
                val pixel = resized[x, y]

                val r = Color.red(pixel)
                val g = Color.green(pixel)
                val b = Color.blue(pixel)

                // 평균 기반 grayscale 변환
                val gray = (0.299f * r + 0.587f * g + 0.114f * b) / 255.0f  // 더 정확한 그레이스케일
                val inverted = 1.0f - gray  // 색상 반전 (흰색 배경->검은색, 검은색 글씨->흰색)
                result[0][y][x] = if(inverted > 0.3f) 0.0f else 1.0f  // 이진화 처리
                
                pixelCount++
                if (gray > 0.5f) whitePixels++
            }
        }
        
        Log.d("DigitClassifier", "White pixels: $whitePixels / $pixelCount (${(whitePixels * 100f / pixelCount).toInt()}%)")
        
        return result
    }

    fun getProcessedBitmap(bitmap: Bitmap): Bitmap {
        // 원본 이미지 처리
        val resized = if (bitmap.width != 28 || bitmap.height != 28) {
            bitmap.scale(28, 28)
        } else {
            bitmap
        }

        // 결과 저장할 비트맵 생성
        val resultBitmap = createBitmap(28, 28)

        // 변환 알고리즘과 동일한 처리 적용
        for (y in 0 until 28) {
            for (x in 0 until 28) {
                val pixel = resized[x, y]
                val r = Color.red(pixel)
                val g = Color.green(pixel)
                val b = Color.blue(pixel)

                val gray = (0.299f * r + 0.587f * g + 0.114f * b) / 255.0f
                val inverted = 1.0f - gray
                val value = if(inverted > 0.3f) 255 else 0

                resultBitmap[x, y] = Color.rgb(value, value, value)
            }
        }

        return resultBitmap
    }
}
