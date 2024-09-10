import org.example.token.TokenType

class Token(private val tokenType: TokenType, private val value: String,val position: Position) {

    fun getValue(): String {
        return value
    }
    fun getType(): TokenType {
        return tokenType
    }
    fun getPosition(): Position {
        return position
    }
}