package net.ifmain.mnistscan.presentation.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import net.ifmain.mnistscan.presentation.DigitViewModel
import net.ifmain.mnistscan.util.DrawingCanvasManager

@Composable
fun DigitScreen(viewModel: DigitViewModel = hiltViewModel()) {
    val predictedDigit by viewModel.digit.observeAsState(null)
    val processedBitmap by viewModel.processedBitmap.observeAsState(null)
    val isLoading by viewModel.isLoading.observeAsState(false)
    var clearTrigger by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DrawingCanvas(
            modifier = Modifier
                .size(280.dp),
            clearTrigger = clearTrigger
        )


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                viewModel.clearPrediction()
                clearTrigger++
            }) {
                Text("초기화")
            }

            Button(
                onClick = {
                    val bitmap = DrawingCanvasManager.getBitmap()
                    Log.d("DigitScreen", "Bitmap size: ${bitmap.width}x${bitmap.height}")
                    viewModel.classify(bitmap)
                },
                enabled = !isLoading
            ) {
                if (isLoading) {
                    Text("예측 중...")
                } else {
                    Text("예측")
                }
            }
        }

        Text(
            text = "예측된 숫자: ${predictedDigit ?: "?"}",
            style = MaterialTheme.typography.headlineMedium
        )

        // 전처리된 이미지 표시
        processedBitmap?.let { bitmap ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "모델 입력 이미지 (28x28)",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "전처리된 이미지",
                    modifier = Modifier
                        .size(112.dp) // 28x28을 4배 확대
                        .border(1.dp, Color.Gray)
                )
            }
        }
    }
}
