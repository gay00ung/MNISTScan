package net.ifmain.mnistscan.presentation

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import net.ifmain.mnistscan.domain.DigitClassifierUseCase
import javax.inject.Inject

@HiltViewModel
class DigitViewModel @Inject constructor(
    private val classifierUseCase: DigitClassifierUseCase
) : ViewModel() {

    private val _digit = MutableLiveData<Int>()
    val digit: LiveData<Int> = _digit

    fun classify(bitmap: Bitmap) {
        _digit.value = classifierUseCase.classifyDigit(bitmap)
    }
}
