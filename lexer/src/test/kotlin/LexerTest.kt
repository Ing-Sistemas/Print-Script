import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LexerTest {

    @Test
    fun `Variable declaration statements`() {
        val elements = JsonConverter().fromJson("1.0/golden/variable-declaration")
        val result = Lexer("1.0").tokenize(TestingStringIterator(elements.input))
        val testResult = LexerTester().testResult(result, elements.expectedTokenTypes)
        if (testResult.message != "All tokens match the expected values.") throw Exception(testResult.message)
        assert(testResult.passed)
    }

}
