package com.mobileinsights.calculator.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.mobileinsights.calculator.ui.theme.Black
import com.mobileinsights.calculator.viewmodel.CalculatorEvent
import com.mobileinsights.calculator.viewmodel.CalculatorViewModel

@SuppressLint("MutableCollectionMutableState")
@Composable
fun CalculatorComponent(viewModel: CalculatorViewModel) {
    val operatorState = viewModel.buttonState.collectAsState()
    val entryState = viewModel.entryState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Black,
        contentColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom
        ) {
             val fontSize = calculateFontSize(entryState.value)
            InputUIComponent(entryState, fontSize)
            KeyboardUIComponent(
                buttonState = operatorState.value,
                onNumberChange = { entry: Int ->
                    viewModel.onEvent(CalculatorEvent.Number(entry))
                }, onOperatorClick = { operation ->
                    viewModel.onEvent(CalculatorEvent.Calculation(operation))
                }
            )
        }
    }
}