package com.mobileinsights.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceAround
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobileinsights.calculator.ui.theme.Black
import com.mobileinsights.calculator.ui.theme.CalculatorTheme
import com.mobileinsights.calculator.ui.theme.DarkGray
import com.mobileinsights.calculator.ui.theme.LightGray
import com.mobileinsights.calculator.ui.theme.Orange
import com.mobileinsights.calculator.ui.theme.White

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
            val currentState = remember { mutableStateOf(Calculation.NONE) }
            val inputMutableState = remember { mutableStateOf("0") }
            val fontSize = calculateFontSize(inputMutableState.value)
            InputUIComponent(inputMutableState, fontSize)
            KeyboardUIComponent(onNumberChange = { value: Int ->
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
            }, onCalculation = { calculation ->
                when (calculation) {
                    Calculation.AC -> {
                        inputMutableState.value = "0"
                        numbers.value = mutableListOf(0f)
                    }
                    Calculation.PERCENTAGE -> {
                        currentState.value = Calculation.PERCENTAGE
                        numbers.value.add(inputMutableState.value.toFloat())
                        eraser.value = true
                    }
                    Calculation.DIVISION -> {
                        currentState.value = Calculation.DIVISION
                        numbers.value.add(inputMutableState.value.toFloat())
                        eraser.value = true
                    }
                    Calculation.MULTIPLICATION -> {
                        currentState.value = Calculation.MULTIPLICATION
                        numbers.value.add(inputMutableState.value.toFloat())
                        eraser.value = true
                    }
                    Calculation.SUBTRACTION -> {
                        currentState.value = Calculation.SUBTRACTION
                        numbers.value.add(inputMutableState.value.toFloat())
                        eraser.value = true
                    }
                    Calculation.ADDITION -> {
                        currentState.value = Calculation.ADDITION
                        numbers.value.add(inputMutableState.value.toFloat())
                        eraser.value = true
                    }
                    Calculation.EQUALS -> {
                        numbers.value.add(inputMutableState.value.toFloat())
                        val newValue = calculate(numbers.value, currentState.value).toString()
                        inputMutableState.value = newValue
                        numbers.value.clear()
                    }
                    else -> {
                        currentState.value = Calculation.NONE
                    }
                }
            })

        }
    }
}

private fun calculate(numbers: MutableList<Float>, calculation: Calculation): Float {
    if (numbers.isEmpty()) {
        return 0f
    }

    numbers.removeIf { it == 0f }

    var result = numbers[0]

    for (i in 1 until numbers.size) {
        val currentNumber = numbers[i]
        when (calculation) {
            Calculation.PERCENTAGE -> result %= currentNumber
            Calculation.DIVISION -> {
                if (currentNumber != 0f) {
                    result /= currentNumber
                }
            }
            Calculation.MULTIPLICATION -> result *= currentNumber
            Calculation.SUBTRACTION -> result -= currentNumber
            Calculation.ADDITION -> result += currentNumber
            else -> return 0f
        }
    }

    return result
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputUIComponent(mutableValueState: MutableState<String>, fontSize: TextUnit) {
    TextField(
        value = mutableValueState.value,
        onValueChange = { },
        textStyle = MaterialTheme.typography.headlineLarge.copy(
            textAlign = TextAlign.End,
            fontSize = fontSize
        ),
        readOnly = true,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            textColor = LightGray,
            containerColor = Black
        )
    )
}

@Composable
fun KeyboardUIComponent(
    modifier: Modifier  = Modifier.fillMaxWidth(),
    onNumberChange: (Int) -> Unit,
    onCalculation: (Calculation) -> Unit
) {
    Row(modifier = modifier, horizontalArrangement = SpaceAround) {
        RoundedButton(
            text = "AC",
            onCalculationClick = {
                onCalculation(Calculation.AC)
            }
        )
        RoundedButton(
            text = "+/-",
            onCalculationClick = {  }
        )
        RoundedButton(
            text = " % ",
            onCalculationClick = { onCalculation(Calculation.PERCENTAGE) }
        )
        RoundedButton(
            text = "÷",
            calculatorButton = CalculatorButtonType.Sign,
            onCalculationClick = { onCalculation(Calculation.DIVISION) }
        )
    }
    Row(modifier = modifier, horizontalArrangement = SpaceAround) {
        RoundedButton(
            text = "7",
            calculatorButton = CalculatorButtonType.Number,
            onNumberClick = onNumberChange
        )
        RoundedButton(
            text = "8",
            calculatorButton = CalculatorButtonType.Number,
            onNumberClick = onNumberChange
        )
        RoundedButton(
            text = "9",
            calculatorButton = CalculatorButtonType.Number,
            onNumberClick = onNumberChange
        )
        RoundedButton(
            text = "x",
            calculatorButton = CalculatorButtonType.Sign,
            onCalculationClick = { onCalculation(Calculation.MULTIPLICATION) }
        )
    }
    Row(modifier = modifier, horizontalArrangement = SpaceAround) {
        RoundedButton(
            text = "4",
            calculatorButton = CalculatorButtonType.Number,
            onNumberClick = onNumberChange
        )
        RoundedButton(
            text = "5",
            calculatorButton = CalculatorButtonType.Number,
            onNumberClick = onNumberChange
        )
        RoundedButton(
            text = "6",
            calculatorButton = CalculatorButtonType.Number,
            onNumberClick = onNumberChange
        )
        RoundedButton(
            text = "-",
            calculatorButton = CalculatorButtonType.Sign,
            onCalculationClick = {
                onCalculation(Calculation.SUBTRACTION)
            }
        )
    }
    Row(modifier = modifier, horizontalArrangement = SpaceAround) {
        RoundedButton(
            text = "1",
            calculatorButton = CalculatorButtonType.Number,
            onNumberClick = onNumberChange
        )
        RoundedButton(
            text = "2",
            calculatorButton = CalculatorButtonType.Number,
            onNumberClick = onNumberChange
        )
        RoundedButton(
            text = "3",
            calculatorButton = CalculatorButtonType.Number,
            onNumberClick = onNumberChange)
        RoundedButton(
            text = "+",
            calculatorButton = CalculatorButtonType.Sign,
            onCalculationClick = {
                onCalculation(Calculation.ADDITION)
            }
        )
    }
    Row(modifier = modifier, horizontalArrangement = SpaceAround) {
        RoundedButton(
            text = "0",
            calculatorButton = CalculatorButtonType.Number,
            onNumberClick = onNumberChange
        )
        RoundedButton(text = "")
        RoundedButton(text = ",")
        RoundedButton(
            text = "=",
            calculatorButton = CalculatorButtonType.Sign,
            onCalculationClick = {
                onCalculation(Calculation.EQUALS)
            }
        )
    }
}

@Composable
fun RoundedButton(
    text: String,
    calculatorButton: CalculatorButtonType = CalculatorButtonType.SpecialSign,
    modifier: Modifier = Modifier
        .size(90.dp)
        .padding(4.dp),
    onNumberClick: (Int) -> Unit = { },
    onCalculationClick: () -> Unit = { }
) {
    Button(
        modifier = modifier,
        onClick = {
            if (calculatorButton is CalculatorButtonType.Number) {
                onNumberClick(text.toInt())
            } else {
                onCalculationClick.invoke()
            }
        },
        shape = CircleShape,
        colors = getButtonColors(calculatorButton)
    ) {
        Text(text = text, fontSize = 24.sp)
    }
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

@Composable
fun getButtonColors(calculatorButton: CalculatorButtonType) : ButtonColors {
    return when (calculatorButton) {
        is CalculatorButtonType.SpecialSign -> ButtonDefaults.buttonColors(
            containerColor = LightGray,
            contentColor = Black
        )
        is CalculatorButtonType.Sign -> ButtonDefaults.buttonColors(
            containerColor = Orange,
            contentColor = Black
        )
        else -> {
            ButtonDefaults.buttonColors(
                containerColor = DarkGray,
                contentColor = White
            )
        }
    }
}

interface CalculatorButtonType {
    object Number : CalculatorButtonType
    object Sign : CalculatorButtonType
    object SpecialSign : CalculatorButtonType
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorTheme {
        CalculatorComponent()
    }
}