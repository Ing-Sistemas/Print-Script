import org.example.CodeFormatter
import org.junit.jupiter.api.Test

class FormatterTest {

    @Test
    fun `test formatter for file`() {
        val formatter = CodeFormatter()
        val configDirectoryPath = "src/test/resources"
        val codeFilePath = "$configDirectoryPath/code.txt"
        val configFilePath = "$configDirectoryPath/config.json"

        formatter.format(codeFilePath, configFilePath)
    }
}