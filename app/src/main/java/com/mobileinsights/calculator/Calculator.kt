package com.mobileinsights.calculator

sealed class Calculator(private val numbers: MutableList<Float>) {
    private fun checkIfMutableListIsEmpty(): Boolean = numbers.isEmpty()
    private fun remove0ValueItems() {
        numbers.removeIf { it == 0f }
    }

    protected abstract fun performCalculation(result: Float, currentNumber: Float): Float

    operator fun invoke(): Float {
        if (checkIfMutableListIsEmpty()) {
            return 0f
        }
        remove0ValueItems()

        var result = numbers.first()
        for (i in 1 until numbers.size) {
            val currentNumber = numbers[i]
            result = performCalculation(result, currentNumber)
        }
        return result
    }

    class Addition(numbers: MutableList<Float>) : Calculator(numbers) {
        override fun performCalculation(result: Float, currentNumber: Float): Float {
            return result + currentNumber
        }
    }

    class Subtraction(numbers: MutableList<Float>) : Calculator(numbers) {
        override fun performCalculation(result: Float, currentNumber: Float): Float {
            return result - currentNumber
        }
    }

    class Multiplication(numbers: MutableList<Float>) : Calculator(numbers) {
        override fun performCalculation(result: Float, currentNumber: Float): Float {
            return result * currentNumber
        }
    }

    class Division(numbers: MutableList<Float>) : Calculator(numbers) {
        override fun performCalculation(result: Float, currentNumber: Float): Float {
            if (currentNumber != 0f) {
                return result / currentNumber
            }
            // Handle division by zero gracefully
            throw IllegalArgumentException("Division by zero is not allowed.")
        }
    }
}

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
