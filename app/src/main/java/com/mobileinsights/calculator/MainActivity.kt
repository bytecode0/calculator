package com.mobileinsights.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mobileinsights.calculator.ui.InputUIComponent
import com.mobileinsights.calculator.ui.KeyboardUIComponent
import com.mobileinsights.calculator.ui.calculateFontSize
import com.mobileinsights.calculator.ui.theme.Black
import com.mobileinsights.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                CalculatorComponent()
            }
        }
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun CalculatorComponent() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Black,
        contentColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom
        ) {
            val eraserState = remember { mutableStateOf(false) }
            val memoryState: MutableState<Float?> = remember { mutableStateOf(null) }
            val operatorState = remember { mutableStateOf(Operator.AC) }
            val entryState = remember { mutableStateOf("0") }
            val fontSize = calculateFontSize(entryState.value)

            InputUIComponent(entryState, fontSize)
            KeyboardUIComponent(
                operatorState = operatorState,
                onNumberChange = { entry: Int ->
                    enterNumber(
                        entryState = entryState,
                        eraserState = eraserState,
                        entry = entry
                    )
                }, onOperatorClick = { operator ->
                    when (operator) {
                        Operator.AC -> {
                            entryState.value = "0"
                            memoryState.value = null
                        }
                        Operator.PERCENTAGE -> {
                            if (eraserState.value.not()) {
                                entryState.value = (entryState.value.toFloat() / 100).toString()
                            }
                        }
                        Operator.DIVISION -> selectOperator(
                            entryState,
                            eraserState,
                            memoryState,
                            operatorState,
                            operator = Operator.DIVISION
                        )
                        Operator.MULTIPLICATION -> selectOperator(
                            entryState,
                            eraserState,
                            memoryState,
                            operatorState,
                            operator = Operator.MULTIPLICATION
                        )
                        Operator.SUBTRACTION -> selectOperator(
                            entryState,
                            eraserState,
                            memoryState,
                            operatorState,
                            operator = Operator.SUBTRACTION
                        )
                        Operator.ADDITION -> selectOperator(
                            entryState,
                            eraserState,
                            memoryState,
                            operatorState,
                            operator = Operator.ADDITION
                        )
                        Operator.EQUALS -> equals(entryState, eraserState, memoryState, operatorState)
                        Operator.NONE -> TODO()
                        Operator.MORE_LESS -> TODO()
                        Operator.COMMA -> TODO()
                    }
                }
            )
        }
    }
}

fun equals(
    entryState: MutableState<String>,
    eraserState: MutableState<Boolean>,
    memoryState: MutableState<Float?>,
    operatorState: MutableState<Operator>
) {
    val total = calculation(
        actual = memoryState.value ?: 0f,
        entry = entryState.value.toFloat(),
        operatorState.value
    )
    memoryState.value = total
    entryState.value = total.toString()
    eraserState.value = true
    operatorState.value = Operator.AC
}

fun selectOperator(
    entryState: MutableState<String>,
    eraserState: MutableState<Boolean>,
    memoryState: MutableState<Float?>,
    operatorState: MutableState<Operator>,
    operator: Operator
) {
    if (eraserState.value.not())  {
        if (memoryState.value == null) {
            memoryState.value = entryState.value.toFloat()
        } else {
            val total = calculation(
                actual = memoryState.value ?: 0f,
                entry = entryState.value.toFloat(),
                operatorState.value
            )
            memoryState.value = total
            entryState.value = total.toString()
        }
        operatorState.value = operator
        eraserState.value = true
    } else  {
        operatorState.value = operator
    }
}

fun enterNumber(
    entryState: MutableState<String>,
    eraserState: MutableState<Boolean>,
    entry: Int
) {
    if (entryState.value.length < 12) {
        if (eraserState.value) {
            entryState.value = "0"
            eraserState.value = false
        }
        val valueBuilder = StringBuilder()
        if (entryState.value != "0") {
            valueBuilder
                .append(entryState.value)
        }
        valueBuilder.append(entry)
        entryState.value = valueBuilder.toString()
    }
}

private fun calculation(
    actual: Float,
    entry: Float,
    currentOperator: Operator
): Float {
    val calculation = when (currentOperator) {
        Operator.DIVISION -> Calculator.Division(actual, entry)()
        Operator.MULTIPLICATION -> Calculator.Multiplication(actual, entry)()
        Operator.SUBTRACTION -> Calculator.Subtraction(actual, entry)()
        Operator.ADDITION -> Calculator.Addition(actual, entry)()
        else -> 0f
    }
    return calculation
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorTheme {
        CalculatorComponent()
    }
}