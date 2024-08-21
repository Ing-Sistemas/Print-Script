package org.example

fun main() {
    val runner = Runner()

    val codeExample = "let a: number = 5; println(a + a);"

    val codeExample2 = "let a: string = 'ast'; println('Result: ' + a);"

    val codeExample3 = "let a: number = 5; println(3 + a + 6);"

    val codeExample4 = "let a: number = 12; let c: number = a; println(c);"

    val example1 = "let name: string = 'Joe'; let lastName: string = 'Doe'; println(name + ' ' + lastName);"

    val example2 = "let a: number = 12; let b: number = 4; let c: number = a / b; println('Result: ' + c);"

    val example3 = "let a: number = 12; let b: number = 4; a = a / b; println('Result: ' + a);"

    runner.run(codeExample4)

}