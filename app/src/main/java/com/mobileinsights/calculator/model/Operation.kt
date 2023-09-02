package com.mobileinsights.calculator.model

enum class Operation(
    val symbol: String
) {
    NONE(""),
    AC("AC"),
    PLUS_MINUS("+/-"),
    COMMA(","),
    PERCENTAGE("%"),
    DIVISION( "/"),
    MULTIPLICATION( "x"),
    SUBTRACTION("-"),
    ADDITION("+"),
    EQUALS("=")
}