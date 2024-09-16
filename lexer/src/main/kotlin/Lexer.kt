import org.example.token.TokenType

class Lexer(
    private val version: String
) {
    private val tokenPatterns: Map<TokenType, Regex> by lazy {
        TokenPatternProvider().getPatterns(version)
    }
    private var currentLine: String? = null
    private var currentIndex = 0
    private var lineNumber = 0

    fun tokenize(input: Iterator<String>): Iterator<Token> {
        return object : Iterator<Token> {
            override fun hasNext(): Boolean {
                while ((currentLine == null || currentIndex >= currentLine!!.length) && input.hasNext()) {
                    currentLine = input.next()
                    currentIndex = 0
                    lineNumber++
                }
                return currentLine != null && currentIndex < currentLine!!.length
            }
            override fun next(): Token {
                if (!hasNext()) throw NoSuchElementException()
                currentLine?.let { line ->
                    while (currentIndex < line.length) {
                        for ((tokenType, regex) in tokenPatterns) {
                            val matcher = regex.find(line, currentIndex)
                            if (matcher != null && matcher.range.first == currentIndex) {
                                val tokenValue = matcher.value
                                val tokenPosition = Position(currentIndex, lineNumber)
                                currentIndex += tokenValue.length
                                return Token(tokenType, tokenValue, tokenPosition)
                            }
                        }
                        if (line[currentIndex].isWhitespace()) {
                            currentIndex++
                        } else {
                            throw Exception("Unexpected character '${line[currentIndex]}' at line: $lineNumber, column: $currentIndex")
                        }

                    }
                }
                throw NoSuchElementException("No more tokens on the current line")
            }
        }
    }
}
