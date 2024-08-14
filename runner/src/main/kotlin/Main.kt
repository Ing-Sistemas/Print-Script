package org.example

fun main() {
    val runner = Runner()

    val code = "let a: string = 'ast'; println(a);"

    runner.run(code)


}