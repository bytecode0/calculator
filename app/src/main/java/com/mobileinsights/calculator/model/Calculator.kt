package com.mobileinsights.calculator.model

sealed class Calculator(
    private val actual: Float,
    private val number: Float
) {
    protected abstract fun performCalculation(actual: Float, currentNumber: Float): Float

    operator fun invoke(): Float {
        return performCalculation(actual, number)
    }

    class Addition(actual: Float, plus: Float) : Calculator(actual, plus) {
        override fun performCalculation(actual: Float, number: Float): Float {
            return actual + number
        }
    }

    class Subtraction(actual: Float, subtraction: Float) : Calculator(actual, subtraction) {
        override fun performCalculation(actual: Float, number: Float): Float {
            return actual - number
        }
    }

    class Multiplication(actual: Float, multiplication: Float) : Calculator(actual, multiplication) {
        override fun performCalculation(actual: Float, number: Float): Float {
            return actual * number
        }
    }

    class Division(actual: Float, division: Float) : Calculator(actual, division) {
        override fun performCalculation(actual: Float, number: Float): Float {
            if (number != 0f) {
                return actual / number
            }
            // Handle division by zero gracefully
            throw IllegalArgumentException("Division by zero is not allowed.")
        }
    }
}
