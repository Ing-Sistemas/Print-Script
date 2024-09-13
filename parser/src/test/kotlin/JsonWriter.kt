import com.google.gson.GsonBuilder
import com.google.gson.ToNumberPolicy
import org.example.parser.syntactic.SyntacticAnalyzer
import org.example.parser.syntactic.SyntacticSuccess
import java.io.FileWriter

class JsonWriter {
    fun write() {
        val gson = GsonBuilder().setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).setPrettyPrinting().create()
        try {
            val code = "a = -x;"
            val tokens = Lexer().tokenize(code)
            val ast = SyntacticAnalyzer().build(tokens)
            if (ast is SyntacticSuccess) {
                val res = ast.astNode
                val writer: FileWriter = FileWriter("parser/src/test/resources/golden/unaryExpression.json")
                gson.toJson(res, writer)
                writer.close()
            }
        } catch (e: Exception) {
            e.message
        }
    }
}

fun main() {
    JsonWriter().write()
}