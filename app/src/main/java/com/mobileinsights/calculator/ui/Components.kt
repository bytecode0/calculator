package com.mobileinsights.calculator.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        colors = TextFieldDefaults.textFieldColors(
            textColor = LightGray,
            containerColor = Black
        )
    )
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
fun KeyboardUIComponent(
    modifier: Modifier  = Modifier
        .fillMaxWidth()
        .padding(4.dp),
    operatorState: MutableState<Operator>,
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
            currentOperator = operatorState.value,
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
            currentOperator = operatorState.value,
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
            currentOperator = operatorState.value,
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
            currentOperator = operatorState.value,
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
            currentOperator = operatorState.value,
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
    CustomAnimatedButton(
        text = operator.symbol,
        textColor = Black,
        backgroundColor = LightGray,
        onClick = {
            onClick.invoke(operator)
        }
    )
}
@Composable
fun OperatorRoundedButton(
    operator: Operator,
    currentOperator: Operator,
    onClick: (Operator) -> Unit = { }
) {
    val backgroundColor = if (currentOperator == operator) White else Orange
    CustomAnimatedButton(
        text = operator.symbol,
        textColor = Black,
        backgroundColor = backgroundColor,
        onClick = {
            onClick.invoke(operator)
        }
    )
}

@Composable
fun NumberRoundedButton(
    text: String,
    onClick: (Int) -> Unit = { }
) {
    CustomAnimatedButton(
        text = text,
        textColor = White,
        backgroundColor = DarkGray,
        onClick = {
            onClick(text.toInt())
        }
    )
}

@Composable
fun CustomAnimatedButton(
    text: String,
    textColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState()
    val cornerRadius by animateDpAsState(targetValue = if (isPressed.value) 10.dp else 50.dp)

    Box(
        modifier = Modifier
            .background(color = backgroundColor, RoundedCornerShape(cornerRadius))
            .size(90.dp)
            .clip(RoundedCornerShape(cornerRadius))
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple()
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor
        )
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
        is ButtonStyle.SelectedOperator -> ButtonDefaults.buttonColors(
            containerColor = White,
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
