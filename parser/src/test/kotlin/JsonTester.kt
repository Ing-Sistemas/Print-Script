import com.google.gson.GsonBuilder
import com.google.gson.ToNumberPolicy
import java.io.InputStreamReader

class JsonTester {
    fun testJson(resultAST: ASTNode, fileName: String): Boolean {
        val gson = GsonBuilder().setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).disableHtmlEscaping().create()
        var expectedJson = ""
        val classLoader = Thread.currentThread().contextClassLoader
        val inputStream = classLoader.getResourceAsStream("golden/$fileName.json")
        inputStream?.let {
            val reader = InputStreamReader(it)
            val jsonElement = gson.fromJson(reader, Any::class.java)
            expectedJson = gson.toJson(jsonElement) ?: throw Exception("Could not parse expected result into Json")
        } ?: println("File not found")
        val resultJson = gson.toJson(resultAST) ?: throw Exception("Could not parse result into Json")
        return expectedJson == resultJson
    }
}