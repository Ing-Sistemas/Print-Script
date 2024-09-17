import com.google.gson.GsonBuilder
import com.google.gson.ToNumberPolicy

class JsonWriter {
    fun write() {
        val gson = GsonBuilder().setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).setPrettyPrinting().create()
        try {
//            val tokens = Lexer("1.1").tokenize(code)
//            val tokens: List<Token> = listOf(Token(TokenType.SEMICOLON, ";", Position(1, 1)))
//            val ast = SyntacticAnalyzer().build(tokens)
//            if (ast is SyntacticSuccess) {
//                val res = ast.astNode
//                val writer: FileWriter = FileWriter("parser/src/test/resources/golden/unaryExpression.json")
//                gson.toJson(res, writer)
//                writer.close()
//            }
        } catch (e: Exception) {
            e.message
        }
    }
}

fun main() {
    JsonWriter().write()
}