package net.ifmain.mnistscan.domain

import android.graphics.Bitmap
import net.ifmain.mnistscan.data.tflite.DigitClassifier

class DigitClassifierUseCaseImpl(private val classifier: DigitClassifier) : DigitClassifierUseCase {
    override fun classifyDigit(bitmap: Bitmap): Int {
        return classifier.classify(bitmap)
    }
}