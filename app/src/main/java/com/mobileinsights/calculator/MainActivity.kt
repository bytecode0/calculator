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
            val eraser = remember { mutableStateOf(false) }
            val numbers = remember { mutableStateOf(mutableListOf(0f)) }
            val currentState = remember { mutableStateOf(Operator.AC) }
            val inputMutableState = remember { mutableStateOf("0") }
            val fontSize = calculateFontSize(inputMutableState.value)
            InputUIComponent(inputMutableState, fontSize)
            KeyboardUIComponent(
                onNumberChange = { value: Int ->
                    if (inputMutableState.value.length < 12) {
                        if (eraser.value) {
                            inputMutableState.value = "0"
                            eraser.value = false
                        }
                        val valueBuilder = StringBuilder()
                        if (inputMutableState.value != "0") {
                            valueBuilder
                                .append(inputMutableState.value)
                        }
                        valueBuilder.append(value)
                        inputMutableState.value = valueBuilder.toString()
                    }
                }, onOperatorClick = { calculation ->
                    when (calculation) {
                        Operator.AC -> {
                            inputMutableState.value = "0"
                            numbers.value = mutableListOf(0f)
                        }
                        Operator.PERCENTAGE -> {
                            currentState.value = Operator.PERCENTAGE
                            numbers.value.add(inputMutableState.value.toFloat())
                            eraser.value = true
                        }
                        Operator.DIVISION -> {
                            if (eraser.value.not())  {
                                numbers.value.add(inputMutableState.value.toFloat())
                                currentState.value = Operator.DIVISION
                                val newValue = calCurrentState(numbers.value, currentState.value)
                                numbers.value = mutableListOf(newValue)
                                inputMutableState.value = newValue.toString()
                            } else  {
                                currentState.value = Operator.DIVISION
                            }
                            eraser.value = true
                        }
                        Operator.MULTIPLICATION -> {
                            if (eraser.value.not())  {
                                numbers.value.add(inputMutableState.value.toFloat())
                                currentState.value = Operator.MULTIPLICATION
                                val newValue = calCurrentState(numbers.value, currentState.value)
                                numbers.value = mutableListOf(newValue)
                                inputMutableState.value = newValue.toString()
                            } else  {
                                currentState.value = Operator.MULTIPLICATION
                            }
                            eraser.value = true
                        }
                        Operator.SUBTRACTION -> {
                            if (eraser.value.not())  {
                                numbers.value.add(inputMutableState.value.toFloat())
                                currentState.value = Operator.SUBTRACTION
                                val newValue = calCurrentState(numbers.value, currentState.value)
                                numbers.value = mutableListOf(newValue)
                                inputMutableState.value = newValue.toString()
                            } else {
                                currentState.value = Operator.SUBTRACTION
                            }
                            eraser.value = true
                        }
                        Operator.ADDITION -> {
                            if (!eraser.value)  {
                                numbers.value.add(inputMutableState.value.toFloat())
                                currentState.value = Operator.ADDITION
                                val newValue = calCurrentState(numbers.value, currentState.value)
                                numbers.value = mutableListOf(newValue)
                                inputMutableState.value = newValue.toString()
                            } else {
                                currentState.value = Operator.ADDITION
                            }
                            eraser.value = true
                        }
                        Operator.EQUALS -> {
                            numbers.value.add(inputMutableState.value.toFloat())
                            val newValue = calCurrentState(numbers.value, currentState.value)
                            inputMutableState.value = newValue.toString()
                            numbers.value.clear()
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

private fun calCurrentState(
    numbers: MutableList<Float>,
    currentOperator: Operator
): Float {
    val calculation = when (currentOperator) {
        // Operator.PERCENTAGE -> result %= currentNumber
        Operator.DIVISION -> Calculator.Division(numbers)()
        Operator.MULTIPLICATION -> Calculator.Multiplication(numbers)()
        Operator.SUBTRACTION -> Calculator.Subtraction(numbers)()
        Operator.ADDITION -> Calculator.Addition(numbers)()
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