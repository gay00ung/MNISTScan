package net.ifmain.mnistscan.domain

import androidx.lifecycle.MutableLiveData
import net.ifmain.mnistscan.util.DrawingCanvasManager
import javax.inject.Inject

class ClearPredictionResultUseCase @Inject constructor() {
    fun execute(digit: MutableLiveData<Int?>) {
        digit.value = null
        digit.postValue(null)
        DrawingCanvasManager.clear()
    }
}