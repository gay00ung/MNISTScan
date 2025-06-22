package net.ifmain.mnistscan.domain

import android.graphics.Bitmap

interface DigitClassifierUseCase {
    fun classifyDigit(bitmap: Bitmap): Int
}