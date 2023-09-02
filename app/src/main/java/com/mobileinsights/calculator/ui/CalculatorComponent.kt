package com.mobileinsights.calculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobileinsights.calculator.ui.theme.Black
import com.mobileinsights.calculator.viewmodel.CalculatorEvent
import com.mobileinsights.calculator.viewmodel.CalculatorViewModel

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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
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