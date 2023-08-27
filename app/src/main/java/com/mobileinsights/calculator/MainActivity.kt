package com.mobileinsights.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.mobileinsights.calculator.ui.theme.CalculartorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculartorTheme {
                // A surface container using the 'background' color from the theme
                Column {
                    InputUIComponent()
                    KeyboardUIComponent()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputUIComponent() {
    var inputMutableState by remember { mutableStateOf("0") }
    TextField(
        value = inputMutableState,
        onValueChange = { newValue ->
            inputMutableState = newValue
        }
    )
}

@Composable
fun KeyboardUIComponent() {
    Text(text = "Here will be the keyboard")
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculartorTheme {
        InputUIComponent()
        KeyboardUIComponent()
    }
}