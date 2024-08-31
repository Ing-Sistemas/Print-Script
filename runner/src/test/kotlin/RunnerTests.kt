import org.example.Runner
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RunnerTests {
    val runner = Runner()

    @Test
    fun `test case`() {
        val code = "let a: number = 5; println(a + a);"
        runner.run(code)
    }

    @Test
    fun `test case 2`() {
        val code2 = "let a: string = 'ast'; println('Result: ' + a);"
        runner.run(code2)
    }

    @Test
    fun `test case 3`() {
        val code3 = "let a: number = 5; println(3 + 5 + 6);"
        runner.run(code3)
    }

    @Test
    fun `test case 4`() {
        val code4 = "let a: number = 12; let c: number = a; println(c);"
        runner.run(code4)
    }

    @Test
    fun `test case 5`() {
        val code5 = "let name: string = 'Joe'; let lastName: string = 'Doe'; println(name + ' ' + lastName);"
        runner.run(code5)
    }

    @Test
    fun `test case 6`() {
        val code6 = "let a: number = 12; let b: number = 4; let c: number = a / b; println('Result: ' + c);"
        runner.run(code6)
    }

    @Test
    fun `test case 7`() {
        val code7 = "let a: number = 12; let b: number = 4; a = a / b; println('Result: ' + a);"
        runner.run(code7)
    }

    // Invalid cases
    @Test
    fun `test case 8`() {
        val code8 = "let a: number = '12';"
        assertThrows<Exception> { runner.run(code8) }
    }

    @Test
    fun `test case 9`() {
        val code9 = "let a: string = 'hello';"
        runner.run(code9)
    }

    @Test
    fun test9() {
        val invalidCode = "let a: string = 'hello'"
        assertThrows<Exception> { runner.run(invalidCode) }
    }

    @Test
    fun `test binary operations`() {
        val code = "println(4+4); println(4-4); println(4*4); println(4/4);"
        runner.run(code)
    }

    @Test
    fun `test binary operations with number and string`() {
        val code = "println(5 + 'apples');"
        runner.run(code)
    }
}