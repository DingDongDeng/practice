package calculator

import java.util.*

fun main(args: Array<String>) {

    println("Hello My Calculator")
    println("please, input formual for calculate")
    val calculator = Calculator()
    val scaner = Scanner(System.`in`)
    val formula = scaner.nextLine().trim()

    println(calculator.calc(formula))
}