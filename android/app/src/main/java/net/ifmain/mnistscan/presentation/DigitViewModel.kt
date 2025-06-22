package net.ifmain.mnistscan.presentation

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.ifmain.mnistscan.domain.ClearPredictionResultUseCase
import net.ifmain.mnistscan.domain.DigitClassifierUseCase
import javax.inject.Inject

@HiltViewModel
class DigitViewModel @Inject constructor(
    private val classifierUseCase: DigitClassifierUseCase,
    private val clearPredictionResultUseCase: ClearPredictionResultUseCase
) : ViewModel() {

    private val _digit = MutableLiveData<Int?>()
    val digit: LiveData<Int?> = _digit

    private val _processedBitmap = MutableLiveData<Bitmap?>()
    val processedBitmap: LiveData<Bitmap?> = _processedBitmap

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun classify(bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            try {
                val processedBmp = classifierUseCase.getProcessedBitmap(bitmap)
                val result = classifierUseCase.classifyDigit(bitmap)
                
                _processedBitmap.postValue(processedBmp)
                _digit.postValue(result)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun clearPrediction() {
        clearPredictionResultUseCase.execute(_digit)
        _processedBitmap.value = null
    }
}
