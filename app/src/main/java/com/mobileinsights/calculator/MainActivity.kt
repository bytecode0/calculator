package com.mobileinsights.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceAround
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobileinsights.calculator.ui.theme.Black
import com.mobileinsights.calculator.ui.theme.CalculatorTheme
import com.mobileinsights.calculator.ui.theme.LightGray

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Black,
                    contentColor = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        InputUIComponent()
                        KeyboardUIComponent()
                    }
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
        },
        textStyle = MaterialTheme.typography.headlineLarge.copy(textAlign = TextAlign.End),
        readOnly = true,
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 72.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = LightGray,
            containerColor = Black
        )
    )
}

@Composable
fun KeyboardUIComponent(modifier: Modifier  = Modifier.fillMaxWidth()) {
    Row(modifier = modifier, horizontalArrangement = SpaceAround) {
        RoundedButton(text = "AC")
        RoundedButton(text = "+/-")
        RoundedButton(text = " % ")
        RoundedButton(text = "÷")
    }
    Row(modifier = modifier, horizontalArrangement = SpaceAround) {
        RoundedButton(text = "7 ")
        RoundedButton(text = "8")
        RoundedButton(text = "9")
        RoundedButton(text = "x")
    }
    Row(modifier = modifier, horizontalArrangement = SpaceAround) {
        RoundedButton(text = "4")
        RoundedButton(text = "5")
        RoundedButton(text = "6")
        RoundedButton(text = "-")
    }
    Row(modifier = modifier, horizontalArrangement = SpaceAround) {
        RoundedButton(text = "1")
        RoundedButton(text = "2")
        RoundedButton(text = "3")
        RoundedButton(text = "+")
    }
    Row(modifier = modifier, horizontalArrangement = SpaceAround) {
        RoundedButton(text = "0")
        RoundedButton(text = "")
        RoundedButton(text = ",")
        RoundedButton(text = "=")
    }
}

@Composable
fun RoundedButton(
    text: String,
    modifier: Modifier = Modifier
        .size(75.dp)
        .padding(4.dp)
) {
    OutlinedButton(
        modifier = modifier,
        onClick = { /*TODO*/ },
        shape = CircleShape
    ) {
        Text(text = "$text")
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorTheme {
        Surface(
            color = Black,
            contentColor = MaterialTheme.colorScheme.background
        ) {
            Column {
                InputUIComponent()
                KeyboardUIComponent()
            }
        }
    }
}