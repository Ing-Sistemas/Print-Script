package org.example

fun main() {
    val runner = Runner()

    //val code = "let a: number = 5; println(a + a);"

    //val code = "let a: string = 'ast'; println('Result: ' + a);"

    //val code = "let a: number = 5; println(3 + 5 + 6);"

    val code2 = "let a: number = 12; let c: number = a;"

    //val code = "let name: string = 'Joe'; let lastName: string = 'Doe'; println(name + ' ' + lastName);"

    val code = "let a: number = 12; let b: number = 4; let c: number = a / b; println('Result: ' + c);"

    //val code = "let a: number = 12; let b: number = 4; a = a / b; println('Result: ' + a);"

    runner.run(code2)


}