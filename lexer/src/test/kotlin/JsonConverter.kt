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
    fun testResult(resultTokens: Iterator<Token>, expectedTokens: List<TokenType>): TestResult {
        val resultList = mutableListOf<TokenType>()
        while (resultTokens.hasNext()) {
            val token = resultTokens.next()
            resultList.add(token.getType())
        }
        val mismatch = resultList.zip(expectedTokens).find { (tokenType, expectedType) ->
            tokenType != expectedType
        }
        return if (mismatch == null) {
            TestResult(passed = true, message = "All tokens match the expected values.")
        } else {
            val (tokenType, expectedType) = mismatch
            TestResult(
                passed = false,
                message = "Token mismatch: got '$tokenType', expected '$expectedType'"
            )
        }
    }
}
data class TestResult(val passed: Boolean, val message: String)

class TestingStringIterator(private val string: String) : Iterator<String> {
    private var isConsumed = false

    override fun hasNext(): Boolean {
        return !isConsumed
    }

    override fun next(): String {
        if (isConsumed) {
            throw NoSuchElementException("No more elements")
        }
        isConsumed = true
        return string
    }
}
