import com.google.gson.GsonBuilder
import com.google.gson.ToNumberPolicy
import com.printscript.ast.ASTNode
import java.io.InputStreamReader

class JsonTester {
    fun testJson(resultAST: ASTNode, fileName: String, version: String): Boolean {
        val gson = GsonBuilder().setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).disableHtmlEscaping().create()
        var expectedJson = ""
        val classLoader = Thread.currentThread().contextClassLoader
        val inputStream = classLoader.getResourceAsStream("golden/$version/$fileName.json")
        inputStream?.let {
            val reader = InputStreamReader(it)
            val jsonElement = gson.fromJson(reader, Any::class.java)
            expectedJson = gson.toJson(jsonElement) ?: throw Exception("Could not parse expected result into Json")
        } ?: println("File not found")
        val resultJson = gson.toJson(resultAST) ?: throw Exception("Could not parse result into Json")
        return expectedJson == resultJson
    }
    fun testJsonWithIterator(resultAST: Iterator<ASTNode>, fileName: String, version: String): Boolean {
        val gson = GsonBuilder().setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).disableHtmlEscaping().create()
        var expectedJson = ""
        val classLoader = Thread.currentThread().contextClassLoader
        val inputStream = classLoader.getResourceAsStream("golden/$version/$fileName.json")
        inputStream?.let {
            val reader = InputStreamReader(it)
            val jsonElement = gson.fromJson(reader, Any::class.java)
            expectedJson = gson.toJson(jsonElement) ?: throw Exception("Could not parse expected result into Json")
        } ?: println("File not found")
        val resultASTList = mutableListOf<ASTNode>()
        while (resultAST.hasNext()) {
            val astNode = resultAST.next()
            resultASTList.add(astNode)
        }
        val resultJson = gson.toJson(resultASTList) ?: throw Exception("Could not parse result into Json")
        return expectedJson == resultJson
    }
}