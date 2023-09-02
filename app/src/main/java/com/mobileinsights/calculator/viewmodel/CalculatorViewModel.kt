package com.mobileinsights.calculator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CalculatorViewModel : ViewModel() {
    private val _mutableEraserState = MutableStateFlow(false)
    private val _mutableMemoryState =  MutableStateFlow<Float?>(null)
    private val _mutableOperatorState = MutableStateFlow(Operator.AC)
    private val _mutableEntryState = MutableStateFlow<String>("0")

    val operatorState = _mutableOperatorState.asStateFlow()
    val entryState= _mutableEntryState.asStateFlow()

    fun enterNumber(entry: Int) {
        viewModelScope.launch {
            if (_mutableEntryState.value.length < 12) {
                if (_mutableEraserState.value) {
                    _mutableEntryState.value = "0"
                    _mutableEraserState.value = false
                }
                val valueBuilder = StringBuilder()
                if (_mutableEntryState.value != "0") {
                    valueBuilder
                        .append(_mutableEntryState.value)
                }
                valueBuilder.append(entry)
                _mutableEntryState.value = valueBuilder.toString()
            }
        }
    }

    fun onEvent(operator: Operator) {
        viewModelScope.launch {
            when(operator) {
                Operator.NONE -> TODO()
                Operator.AC -> {
                    _mutableEntryState.value = "0"
                    _mutableMemoryState.value = null
                }
                Operator.MORE_LESS -> TODO()
                Operator.PERCENTAGE -> {
                    if (_mutableEraserState.value.not()) {
                        _mutableEntryState.value = (entryState.value.toFloat() / 100).toString()
                    }
                }
                Operator.COMMA -> TODO()
                Operator.DIVISION -> selectOperator(Operator.DIVISION)
                Operator.MULTIPLICATION -> selectOperator(Operator.MULTIPLICATION)
                Operator.SUBTRACTION -> selectOperator(Operator.SUBTRACTION)
                Operator.ADDITION -> selectOperator(Operator.ADDITION)
                Operator.EQUALS -> equals()
            }
        }
    }

    private fun equals() {
        val total = calculation(
            _mutableMemoryState.value ?: 0f,
            _mutableEntryState.value.toFloat(),
            _mutableOperatorState.value
        )
        _mutableMemoryState.value = total
        _mutableEntryState.value = total.toString()
        _mutableEraserState.value = true
        _mutableOperatorState.value = Operator.AC
    }

    private fun selectOperator(
        operator: Operator
    ) {
        if (_mutableEraserState.value.not())  {
            if (_mutableMemoryState.value == null) {
                _mutableMemoryState.value = _mutableEntryState.value.toFloat()
            } else {
                val total = calculation(
                    actual = _mutableMemoryState.value ?: 0f,
                    entry = _mutableEntryState.value.toFloat(),
                    _mutableOperatorState.value
                )
                _mutableMemoryState.value = total
                _mutableEntryState.value = total.toString()
            }
            _mutableOperatorState.value = operator
            _mutableEraserState.value = true
        } else  {
            _mutableOperatorState.value = operator
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
}