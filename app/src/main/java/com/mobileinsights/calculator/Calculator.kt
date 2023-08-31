package com.mobileinsights.calculator

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