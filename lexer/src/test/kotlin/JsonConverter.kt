import com.google.gson.annotations.SerializedName
import org.example.token.TokenType
import com.google.gson.Gson
import java.io.InputStreamReader

class JsonConverter {
    fun fromJson(fileName: String): LexerTestElements{
        val classLoader = object {}.javaClass.classLoader
        val inputStream = classLoader.getResourceAsStream("$fileName.json")
            ?: throw IllegalArgumentException("File not found!")
        val reader = InputStreamReader(inputStream)
        val gson = Gson()
        val lexerTestInput = gson.fromJson(reader, LexerTestInput::class.java)
        reader.close()
        val version = lexerTestInput.version
        val inputString = lexerTestInput.input
        val expectedTokens = lexerTestInput.expectedTokens
        return LexerTestElements(version, inputString,expectedTokens)
    }
}

data class LexerTestElements(
    val version: String,
    val input: String,
    val expectedTokenTypes: List<TokenType>,
)

data class LexerTestInput(
    @SerializedName("version") val version: String,
    @SerializedName("input") val input: String,
    @SerializedName("expectedTokens") val expectedTokens: List<TokenType>
)

class LexerTester {
    fun testResult(resultTokens: List<Token>, expectedTokens: List<TokenType>): Boolean {
        return resultTokens.zip(expectedTokens).all { (token, expectedType) ->
            token.getType() == expectedType
        }
    }
}