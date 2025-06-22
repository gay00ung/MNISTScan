package net.ifmain.mnistscan.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.ifmain.mnistscan.presentation.DigitViewModel
import net.ifmain.mnistscan.util.DrawingCanvasManager

@Composable
fun DigitScreen(viewModel: DigitViewModel) {
    val predictedDigit by viewModel.digit.observeAsState()
    var clearCanvas by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DrawingCanvas(
            modifier = Modifier
                .size(280.dp)
        )


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                DrawingCanvasManager.clear()
                clearCanvas = true
                clearCanvas = false
            }) {
                Text("초기화")
            }

            Button(onClick = {
                val bitmap = DrawingCanvasManager.getBitmap()
                viewModel.classify(bitmap)
            }) {
                Text("예측")
            }
        }

        Text(
            text = "예측된 숫자: ${predictedDigit ?: "?"}",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
