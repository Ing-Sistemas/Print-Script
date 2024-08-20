package org.example

fun main() {
    val runner = Runner()

    val code1 = "let a: number = 5; println(a + a);"
    val code2 = "let a: string = 'ast'; println('Result: ' + a);"
    val code3 = "let a: number = 5; println(3 + 5 + 6);"
    val code4 = "let a: number = 12; let c: number = a;"
    val code5 = "let name: string = 'Joe'; let lastName: string = 'Doe'; println(name + ' ' + lastName);"
    val code6 = "let a: number = 12; let b: number = 4; let c: number = a / b; println('Result: ' + c);"
    val code7 = "let a: number = 12; let b: number = 4; a = a / b; println('Result: ' + a);"
    //invalid cases
    val code8 = "let a: string = 12;"
    val code9 = "let a: string = 'hello';"

    runner.run(code8)


}