import org.example.token.TokenType

class Lexer(
    private val version: String
) {
    private val tokenPatterns: Map<TokenType, Regex> by lazy {
        TokenPatternProvider().getPatterns(version)
    }
    fun tokenize(input: String): Iterator<Token> {
        return object : Iterator<Token> {
            var currentIndex = 0
            var lineNumber = 1

            override fun hasNext(): Boolean {
                return currentIndex < input.length
            }
            override fun next(): Token {
                if (!hasNext()) {
                    throw NoSuchElementException()
                }
                val token = tokenizeLine(input)
                if (token != null) {
                    return token
                } else {
                    throw NoSuchElementException()
                }
            }

            private fun tokenizeLine(input: String): Token? {
                while (currentIndex < input.length) {
                    for ((tokenType, regex) in tokenPatterns) {
                        val matcher = regex.find(input, currentIndex)
                        if (matcher != null && matcher.range.first == currentIndex) {
                            val tokenValue = matcher.value
                            val tokenPosition = Position(currentIndex + 1, lineNumber)
                            lineNumber += tokenValue.count { it == '\n' }
                            currentIndex += tokenValue.length
                            return Token(
                                tokenType,
                                tokenValue,
                                tokenPosition
                            )
                        }
                    }

                    if (input[currentIndex].isWhitespace()) {
                        if (input[currentIndex] == '\n') {
                            lineNumber++
                        }
                        currentIndex++
                    } else {
                        throw Exception("Unexpected character '${input[currentIndex]}' at line: $lineNumber, column: ${currentIndex + 1}")
                    }
                }
                return null
            }
        }
    }
}