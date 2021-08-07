package calculator

import java.util.*

fun main(args: Array<String>) {

    /**
     *TODO
     * null 관련된 핸들링을 해보자...
     * :Int? 인데 일부로 null도 넣어보고... 어디까지 컴파일에서 잡아주는지도...
     */
    val MODE = "KOTLIN"
    println(
        """Hello,
            |This is ${MODE.toUpperCase()} Calculator""".trimMargin()
    )
    println("please, input formual for calculate")
    val calculator = Calculator()
    val scaner = Scanner(System.`in`)
    val formula = scaner.nextLine().trim()

    println(calculator.calc(formula))
}