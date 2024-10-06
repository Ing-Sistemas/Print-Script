import com.google.gson.*
import com.printscript.ast.ASTNode
import com.printscript.lexer.Lexer
import com.printscript.parser.ASTIterator
import com.printscript.parser.Parser
import java.io.File
import java.io.FileWriter

class JsonWriter {
    fun write() {
        val gson = GsonBuilder().setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).setPrettyPrinting().create()
        try {
            val file = File("parser/src/test/resources/if-main.ps")
            println(file.isFile)
            val readerIterator = TestReadIterator().getLineIterator(file.inputStream())
            val tokens = Lexer("1.1").tokenize(readerIterator)
            val astIterator = ASTIterator(tokens, Parser())
            val astList = mutableListOf<ASTNode>()
            while (astIterator.hasNext()) {
                val ast = astIterator.next()
                astList.add(ast)
            }
            val writer: FileWriter = FileWriter("parser/src/test/resources/golden/1.1/if-call.json")
            gson.toJson(astList, writer)
            writer.close()
        } catch (e: Exception) {
            println(e.message)
        }
    }
}

fun main() {
    JsonWriter().write()
}