package net.ifmain.mnistscan.domain

import android.graphics.Bitmap
import net.ifmain.mnistscan.data.tflite.DigitClassifier
import javax.inject.Inject

class DigitClassifierUseCaseImpl @Inject constructor(
    private val classifier: DigitClassifier
) : DigitClassifierUseCase {
    override fun classifyDigit(bitmap: Bitmap): Int {
        return classifier.classify(bitmap)
    }
    
    override fun getProcessedBitmap(bitmap: Bitmap): Bitmap {
        return classifier.getProcessedBitmap(bitmap)
    }
}