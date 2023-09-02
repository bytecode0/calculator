package com.mobileinsights.calculator.viewmodel

enum class Operator(
    val symbol: String
) {
    NONE(""),
    AC("AC"),
    MORE_LESS("+/-"),
    PERCENTAGE("%"),
    COMMA(","),
    DIVISION( "/"),
    MULTIPLICATION( "x"),
    SUBTRACTION("-"),
    ADDITION("+"),
    EQUALS( "=")
}