import org.junit.jupiter.api.Test

class LexerTest {
    @Test
    fun `Variable declaration statements`() {
        val elements = JsonConverter().fromJson("1.0/variable-declaration")
        val result = Lexer("1.0").tokenize(elements.input)
        assert(LexerTester().testResult(result,elements.expectedTokenTypes))
    }
}
