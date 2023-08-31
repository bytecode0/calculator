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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.mobileinsights.calculator.ui.InputUIComponent
import com.mobileinsights.calculator.ui.KeyboardUIComponent
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
                            currentState.value = Operator.DIVISION
                            numbers.value.add(inputMutableState.value.toFloat())
                            eraser.value = true
                        }
                        Operator.MULTIPLICATION -> {
                            currentState.value = Operator.MULTIPLICATION
                            numbers.value.add(inputMutableState.value.toFloat())
                            eraser.value = true
                        }
                        Operator.SUBTRACTION -> {
                            currentState.value = Operator.SUBTRACTION
                            numbers.value.add(inputMutableState.value.toFloat())
                            eraser.value = true
                        }
                        Operator.ADDITION -> {
                            currentState.value = Operator.ADDITION
                            numbers.value.add(inputMutableState.value.toFloat())
                            eraser.value = true
                        }
                        Operator.EQUALS -> {
                            numbers.value.add(inputMutableState.value.toFloat())
                            val newValue = calculate(numbers.value, currentState.value).toString()
                            inputMutableState.value = newValue
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

private fun calculate(numbers: MutableList<Float>, calculation: Operator): Float {
    if (numbers.isEmpty()) {
        return 0f
    }

    numbers.removeIf { it == 0f }

    var result = numbers[0]

    for (i in 1 until numbers.size) {
        val currentNumber = numbers[i]
        when (calculation) {
            Operator.PERCENTAGE -> result %= currentNumber
            Operator.DIVISION -> {
                if (currentNumber != 0f) {
                    result /= currentNumber
                }
            }
            Operator.MULTIPLICATION -> result *= currentNumber
            Operator.SUBTRACTION -> result -= currentNumber
            Operator.ADDITION -> result += currentNumber
            else -> return 0f
        }
    }

    return result
}

@Composable
fun calculateFontSize(text: String): TextUnit {
    val baseFontSize = 94.sp
    val maxDigitsBeforeScaling = 6

    val scaleFactor = when {
        text.length <= maxDigitsBeforeScaling -> 1f
        else -> (maxDigitsBeforeScaling.toFloat() / text.length)
    }

    return baseFontSize * scaleFactor
}


@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorTheme {
        CalculatorComponent()
    }
}