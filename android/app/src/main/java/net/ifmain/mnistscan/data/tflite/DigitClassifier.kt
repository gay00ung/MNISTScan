package net.ifmain.mnistscan.data.tflite

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter

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
        TODO()
    }
}
