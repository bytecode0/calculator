package com.mobileinsights.calculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobileinsights.calculator.Operator
import com.mobileinsights.calculator.ui.theme.Black
import com.mobileinsights.calculator.ui.theme.DarkGray
import com.mobileinsights.calculator.ui.theme.LightGray
import com.mobileinsights.calculator.ui.theme.Orange
import com.mobileinsights.calculator.ui.theme.White

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
    onOperatorClick: (Operator) -> Unit
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Absolute.SpaceAround) {
        SpecialOperatorRoundedButton(
            operator = Operator.AC,
            onClick = {
                onOperatorClick(Operator.AC)
            }
        )
        SpecialOperatorRoundedButton(
            operator = Operator.MORE_LESS
        )
        SpecialOperatorRoundedButton(
            operator = Operator.PERCENTAGE,
            onClick = onOperatorClick
        )
        OperatorRoundedButton(
            operator = Operator.DIVISION,
            onClick =  onOperatorClick
        )
    }
    Row(modifier = modifier, horizontalArrangement = Arrangement.Absolute.SpaceAround) {
        NumberRoundedButton(
            text = "7",
            onClick = onNumberChange
        )
        NumberRoundedButton(
            text = "8",
            onClick = onNumberChange
        )
        NumberRoundedButton(
            text = "9",
            onClick = onNumberChange
        )
        OperatorRoundedButton(
            operator = Operator.MULTIPLICATION,
            onClick =  onOperatorClick
        )
    }
    Row(modifier = modifier, horizontalArrangement = Arrangement.Absolute.SpaceAround) {
        NumberRoundedButton(
            text = "4",
            onClick = onNumberChange
        )
        NumberRoundedButton(
            text = "5",
            onClick = onNumberChange
        )
        NumberRoundedButton(
            text = "6",
            onClick = onNumberChange
        )
        OperatorRoundedButton(
            operator = Operator.SUBTRACTION,
            onClick =  onOperatorClick
        )
    }
    Row(modifier = modifier, horizontalArrangement = Arrangement.Absolute.SpaceAround) {
        NumberRoundedButton(
            text = "1",
            onClick = onNumberChange
        )
        NumberRoundedButton(
            text = "2",
            onClick = onNumberChange
        )
        NumberRoundedButton(
            text = "3",
            onClick = onNumberChange
        )
        OperatorRoundedButton(
            operator = Operator.ADDITION,
            onClick =  onOperatorClick
        )
    }
    Row(modifier = modifier, horizontalArrangement = Arrangement.Absolute.SpaceAround) {
        NumberRoundedButton(
            text = "0",
            onClick = onNumberChange
        )
        SpecialOperatorRoundedButton(
            operator = Operator.NONE
        )
        SpecialOperatorRoundedButton(
            operator = Operator.COMMA
        )
        OperatorRoundedButton(
            operator = Operator.EQUALS,
            onClick =  onOperatorClick
        )
    }
}

@Composable
fun SpecialOperatorRoundedButton(
    operator: Operator,
    modifier: Modifier = Modifier
        .size(90.dp)
        .padding(4.dp),
    onClick: (Operator) -> Unit = { }
) {
    CustomRoundedButton(
        text = operator.symbol,
        buttonStyle = ButtonStyle.SpecialOperator,
        modifier = modifier,
        onClick = {
            onClick.invoke(operator)
        }
    )
}
@Composable
fun OperatorRoundedButton(
    operator: Operator,
    modifier: Modifier = Modifier
        .size(90.dp)
        .padding(4.dp),
    onClick: (Operator) -> Unit = { }
) {
    CustomRoundedButton(
        text = operator.symbol,
        buttonStyle = ButtonStyle.Operator,
        modifier = modifier,
        onClick = {
            onClick.invoke(operator)
        }
    )
}

@Composable
fun NumberRoundedButton(
    text: String,
    modifier: Modifier = Modifier
        .size(90.dp)
        .padding(4.dp),
    onClick: (Int) -> Unit = { }
) {
    CustomRoundedButton(
        text = text,
        buttonStyle = ButtonStyle.Number,
        modifier = modifier,
        onClick = {
            onClick(text.toInt())
        }
    )
}

@Composable
fun CustomRoundedButton(
    text: String,
    buttonStyle: ButtonStyle = ButtonStyle.SpecialOperator,
    modifier: Modifier = Modifier
        .size(90.dp)
        .padding(4.dp),
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = CircleShape,
        colors = getButtonColors(buttonStyle)
    ) {
        Text(text = text, fontSize = 24.sp)
    }
}

@Composable
fun getButtonColors(buttonStyle: ButtonStyle) : ButtonColors {
    return when (buttonStyle) {
        is ButtonStyle.SpecialOperator -> ButtonDefaults.buttonColors(
            containerColor = LightGray,
            contentColor = Black
        )
        is ButtonStyle.Operator -> ButtonDefaults.buttonColors(
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
