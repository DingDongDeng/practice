package calculator

import java.lang.IllegalArgumentException

class Calculator {

    fun calc(a: Double, operator: String, b: Double): Double {
        when (operator) {
            "+" -> return plus(a, b)
            "-" -> return minus(a, b)
            "*" -> return multi(a, b)
            "/" -> return devide(a, b)
        }
        throw IllegalArgumentException(operator)
    }

    fun calc(formula: String): Double {
        val formulaParts = formula.replace("\\s".toRegex(),"").toCharArray().map { it.toString() }
        return calc(formulaParts[0].toDouble(), formulaParts[1], formulaParts[2].toDouble())
    }

    private fun plus(a: Double, b: Double) = a + b
    private fun minus(a: Double, b: Double) = a - b
    private fun multi(a: Double, b: Double) = a * b
    private fun devide(a: Double, b: Double) = a / b
}