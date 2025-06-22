package net.ifmain.mnistscan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import net.ifmain.mnistscan.presentation.ui.DigitScreen
import net.ifmain.mnistscan.ui.theme.MNISTScanTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MNISTScanTheme {
                DigitScreen()
            }
        }
    }
}