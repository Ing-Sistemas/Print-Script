import org.example.token.TokenMatcher

//coordina tokenization
//obs: usaría estretegias de tokenization
class Lexer {
    private var position = 0
    private val tokenMatcher = TokenMatcher()

    fun tokenize(input: String): List<Token> {
        val tokenList = mutableListOf<Token>()
        while (position < input.length) {
            val token = nextToken(input)
            if (token != null) {
                tokenList.add(token)
            }
        }
        return tokenList
    }

    private fun nextToken(input: String): Token? {
        if (position >= input.length) {
            return null
        }
        val restOfString =  input.substring(position)

        if (restOfString[0].isWhitespace()) {
            position++
            return nextToken(input)
        }

        val isMatch = tokenMatcher.match(input, position)
        if (isMatch != null) {
            position = isMatch.nextPosition
            return isMatch.token
        }
        throw Exception("Bad character")
    }
}