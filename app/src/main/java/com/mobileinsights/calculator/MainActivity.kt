package com.mobileinsights.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
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
            val inputMutableState = remember { mutableStateOf("0") }
            val fontSize = calculateFontSize(inputMutableState.value)
            InputUIComponent(inputMutableState, fontSize)
            KeyboardUIComponent { onValueChange ->
                if (inputMutableState.value.length < 9) {
                    val valueBuilder = StringBuilder()
                    if (inputMutableState.value != "0") {
                        valueBuilder
                            .append(inputMutableState.value)
                    }
                    valueBuilder.append(onValueChange)

                    inputMutableState.value = valueBuilder.toString()
                }
            }
        }
    }
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
        maxLines = 1,
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
    onValueChange: (String) -> Unit
) {
    Row(modifier = modifier, horizontalArrangement = SpaceAround) {
        RoundedButton(text = "AC", calculatorButton = CalculatorButton.SpecialOperatorButton)
        RoundedButton(text = "+/-", calculatorButton = CalculatorButton.SpecialOperatorButton)
        RoundedButton(text = " % ", calculatorButton = CalculatorButton.SpecialOperatorButton)
        RoundedButton(text = "÷", calculatorButton = CalculatorButton.OperatorButton)
    }
    Row(modifier = modifier, horizontalArrangement = SpaceAround) {
        RoundedButton(text = "7", onClick = onValueChange)
        RoundedButton(text = "8", onClick = onValueChange)
        RoundedButton(text = "9", onClick = onValueChange)
        RoundedButton(text = "x", calculatorButton = CalculatorButton.OperatorButton)
    }
    Row(modifier = modifier, horizontalArrangement = SpaceAround) {
        RoundedButton(text = "4", onClick = onValueChange)
        RoundedButton(text = "5", onClick = onValueChange)
        RoundedButton(text = "6", onClick = onValueChange)
        RoundedButton(text = "-", calculatorButton = CalculatorButton.OperatorButton)
    }
    Row(modifier = modifier, horizontalArrangement = SpaceAround) {
        RoundedButton(
            text = "1",
            onClick = onValueChange
        )
        RoundedButton(
            text = "2",
            onClick = onValueChange
        )
        RoundedButton(
            text = "3",
            onClick = onValueChange
        )
        RoundedButton(
            text = "+",
            calculatorButton = CalculatorButton.OperatorButton
        )
    }
    Row(modifier = modifier, horizontalArrangement = SpaceAround) {
        RoundedButton(text = "0", onClick = onValueChange)
        RoundedButton(text = "")
        RoundedButton(text = ",")
        RoundedButton(text = "=", calculatorButton = CalculatorButton.OperatorButton)
    }
}

@Composable
fun RoundedButton(
    text: String,
    calculatorButton: CalculatorButton = CalculatorButton.NumberButton,
    modifier: Modifier = Modifier
        .size(90.dp)
        .padding(4.dp),
    onClick: (String) -> Unit = { }
) {
    Button(
        modifier = modifier,
        onClick = {
            onClick(text)
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
    val maxDigitsBeforeScaling = 7

    val scaleFactor = when {
        text.length <= maxDigitsBeforeScaling -> 1f
        else -> (maxDigitsBeforeScaling.toFloat() / text.length)
    }

    return baseFontSize * scaleFactor
}

@Composable
fun getButtonColors(calculatorButton: CalculatorButton) : ButtonColors {
    return when (calculatorButton) {
        is CalculatorButton.SpecialOperatorButton -> ButtonDefaults.buttonColors(
            containerColor = LightGray,
            contentColor = Black
        )
        is CalculatorButton.OperatorButton -> ButtonDefaults.buttonColors(
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

sealed class CalculatorButton {
    object SpecialOperatorButton : CalculatorButton()
    object OperatorButton : CalculatorButton()

    object NumberButton : CalculatorButton()
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorTheme {
        CalculatorComponent()
    }
}