package org.example

fun main() {
    val runner = Runner()

    val code = "let a: number = 5; println(a);"

    val result = runner.run(code)
    println(result)

}