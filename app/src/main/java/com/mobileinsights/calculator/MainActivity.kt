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
                onNumberChange = { value: Int ->
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
                        valueBuilder.append(value)
                        entryState.value = valueBuilder.toString()
                    }
                }, onOperatorClick = { operator ->
                    when (operator) {
                        Operator.AC -> {
                            entryState.value = "0"
                            memoryState.value = 0f
                        }
                        Operator.PERCENTAGE -> {
                            operatorState.value = Operator.PERCENTAGE
                            memoryState.value = entryState.value.toFloat()
                            eraserState.value = true
                        }
                        Operator.DIVISION -> {
                            if (eraserState.value.not())  {
                                operatorState.value = Operator.DIVISION
                                val division = Calculator.Division(
                                    actual = memoryState.value as Float,
                                    division = entryState.value.toFloat()
                                )()
                                memoryState.value = division
                                entryState.value = division.toString()
                                eraserState.value = true
                            } else  {
                                operatorState.value = Operator.DIVISION
                            }
                        }
                        Operator.MULTIPLICATION -> {
                            if (eraserState.value.not())  {
                                operatorState.value = Operator.MULTIPLICATION
                                if (memoryState.value == null) {
                                    memoryState.value = entryState.value.toFloat()
                                } else {
                                    val multiplication = Calculator.Multiplication(
                                        actual = memoryState.value as Float,
                                        multiplication = entryState.value.toFloat()
                                    )()
                                    memoryState.value = multiplication
                                    entryState.value = multiplication.toString()
                                }
                                eraserState.value = true
                            } else  {
                                operatorState.value = Operator.MULTIPLICATION
                            }
                        }
                        Operator.SUBTRACTION -> {
                            if (eraserState.value.not())  {
                                operatorState.value = Operator.SUBTRACTION
                                if (memoryState.value == null) {
                                    memoryState.value = entryState.value.toFloat()
                                } else {
                                    val subtraction = Calculator.Subtraction(
                                        actual = memoryState.value as Float,
                                        subtraction = entryState.value.toFloat()
                                    )()
                                    memoryState.value = subtraction
                                    entryState.value = subtraction.toString()
                                }
                                eraserState.value = true
                            } else {
                                operatorState.value = Operator.SUBTRACTION
                            }
                        }
                        Operator.ADDITION -> {
                            if (!eraserState.value)  {
                                operatorState.value = Operator.ADDITION
                                if (memoryState.value == null) {
                                    memoryState.value = entryState.value.toFloat()
                                } else {
                                    val addition = Calculator.Addition(
                                        actual = memoryState.value as Float,
                                        plus = entryState.value.toFloat()
                                    )()
                                    memoryState.value = addition
                                    entryState.value = addition.toString()
                                }
                                eraserState.value = true
                            } else {
                                operatorState.value = Operator.ADDITION
                            }
                        }
                        Operator.EQUALS -> {
                            val total = calculation(
                                actual = memoryState.value ?: 0f,
                                entry = entryState.value.toFloat(),
                                operatorState.value
                            )
                            memoryState.value = total
                            entryState.value = total.toString()
                            eraserState.value = true
                        }
                        Operator.NONE -> TODO()
                        Operator.MORE_LESS -> TODO()
                        Operator.COMMA -> TODO()
                    }
                }
            )
        }
    }
}

private fun calculation(
    actual: Float,
    entry: Float,
    currentOperator: Operator
): Float {
    val calculation = when (currentOperator) {
        // Operator.PERCENTAGE -> result %= currentNumber
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