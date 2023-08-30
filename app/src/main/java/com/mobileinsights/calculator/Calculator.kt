package com.mobileinsights.calculator

enum class Calculation(
    val signName: String,
    val symbol: String
) {
    NONE("", ""),
    AC("AC", "AC"),
    PERCENTAGE("Percentage", "%"),
    DIVISION("Division", "/"),
    MULTIPLICATION("Multiplication", "x"),
    SUBTRACTION("Subtraction", "-"),
    ADDITION("Addition", "+"),
    EQUALS("Equals", "=")
}