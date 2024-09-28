import com.printscript.lexer.Lexer
import org.junit.jupiter.api.Test

class LexerTest {

    @Test
    fun `Variable declaration statement`() {
        val elements = JsonConverter().fromJson("1.0/golden/variable_declaration")
        val result = Lexer(elements.version).tokenize(TestingStringIterator(elements.input))
        val testResult = LexerTester().testResult(result, elements.expectedTokenTypes)
        if (testResult.message != "All tokens match the expected values.") throw Exception(testResult.message)
        assert(testResult.passed)
    }

    @Test
    fun `Println fun call statement`() {
        val elements = JsonConverter().fromJson("1.0/golden/println_call")
        val result = Lexer(elements.version).tokenize(TestingStringIterator(elements.input))
        val testResult = LexerTester().testResult(result, elements.expectedTokenTypes)
        if (testResult.message != "All tokens match the expected values.") throw Exception(testResult.message)
        assert(testResult.passed)
    }

    @Test
    fun `Binary Expression`() {
        val elements = JsonConverter().fromJson("1.0/golden/binary_expression")
        val result = Lexer(elements.version).tokenize(TestingStringIterator(elements.input))
        val testResult = LexerTester().testResult(result, elements.expectedTokenTypes)
        if (testResult.message != "All tokens match the expected values.") throw Exception(testResult.message)
        assert(testResult.passed)
    }

    @Test
    fun `If call statement`() {
        val elements = JsonConverter().fromJson("1.1/golden/if_function_call")
        val result = Lexer(elements.version).tokenize(TestingStringIterator(elements.input))
        val testResult = LexerTester().testResult(result, elements.expectedTokenTypes)
        if (testResult.message != "All tokens match the expected values.") throw Exception(testResult.message)
        assert(testResult.passed)
    }

    @Test
    fun `Boolean variable declaration statement`() {
        val elements = JsonConverter().fromJson("1.1/golden/boolean_variable_declaration")
        val result = Lexer(elements.version).tokenize(TestingStringIterator(elements.input))
        val testResult = LexerTester().testResult(result, elements.expectedTokenTypes)
        if (testResult.message != "All tokens match the expected values.") throw Exception(testResult.message)
        assert(testResult.passed)
    }
}