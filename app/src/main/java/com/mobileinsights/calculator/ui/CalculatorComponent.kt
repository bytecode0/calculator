package com.mobileinsights.calculator.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.mobileinsights.calculator.ui.theme.Black
import com.mobileinsights.calculator.viewmodel.CalculatorViewModel

@SuppressLint("MutableCollectionMutableState")
@Composable
fun CalculatorComponent(viewModel: CalculatorViewModel) {
    val operatorState = viewModel.operatorState.collectAsState()
    val entryState = viewModel.entryState.collectAsState()


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Black,
        contentColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom
        ) {
            //val eraserState = remember { mutableStateOf(eraserState.value) }
            // val memoryState: MutableState<Float?> = remember { mutableStateOf(memoryState.value) }
            val operatorState = remember { mutableStateOf(operatorState.value) }
            val entryState = remember { entryState }
            val fontSize = calculateFontSize(entryState.value)
            InputUIComponent(entryState, fontSize)
            KeyboardUIComponent(
                operatorState = operatorState,
                onNumberChange = { entry: Int ->
                    viewModel.enterNumber(entry = entry)
                }, onOperatorClick = { operator ->
                    viewModel.onEvent(operator)
                }
            )
        }
    }
}