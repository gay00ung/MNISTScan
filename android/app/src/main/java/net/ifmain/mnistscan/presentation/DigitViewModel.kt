package net.ifmain.mnistscan.presentation

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.ifmain.mnistscan.domain.DigitClassifierUseCase

class DigitViewModel(
    private val classifierUseCase: DigitClassifierUseCase
) : ViewModel() {

    private val _digit = MutableLiveData<Int>()
    val digit: LiveData<Int> = _digit

    fun classify(bitmap: Bitmap) {
        _digit.value = classifierUseCase.classifyDigit(bitmap)
    }
}
