// TokenIterator.kt
import org.example.token.TokenType

class TokenIterator(
    private val input: Iterator<String>,
    private val tokenPatterns: Map<TokenType, Regex>,
) : Iterator<Token> {
    private var currentLine: String? = null
    private var currentIndex = 0
    private var lineNumber = 0

    override fun hasNext(): Boolean {
        return input.hasNext() || (currentLine != null && currentIndex < currentLine!!.length)
    }

    override fun next(): Token {
        while ((currentLine == null || currentIndex >= currentLine!!.length) && input.hasNext()) {
            currentLine = input.next()
            currentIndex = 0
            lineNumber++
        }
        currentLine?.let { line ->
            while (currentIndex < line.length) {
                for ((tokenType, regex) in tokenPatterns) {
                    val matcher = regex.find(line, currentIndex)
                    if (matcher != null && matcher.range.first == currentIndex) {
                        val tokenValue = matcher.value
                        val tokenPosition = Position(currentIndex, lineNumber)
                        currentIndex += tokenValue.length
                        if (tokenType == TokenType.LITERAL_STRING) {
                            return Token(tokenType, tokenValue.substring(1, tokenValue.length - 1), tokenPosition)
                        }
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