import com.printscript.formatter.CodeFormatter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

class FormatterTest {
    private val configDirectoryPath = "src/test/resources"

    @Test
    fun `test formatter for file`() {
        val formatter = CodeFormatter()
        val codeFilePath = "$configDirectoryPath/toFormatCases/defaultConfigCase.txt"
        val configFilePath = "$configDirectoryPath/configurations/config.json"
        val correctFormatFilePath = "$configDirectoryPath/correctCases/defaultConfigCase.txt"

        formatter.format(codeFilePath, configFilePath)
        val formattedCode = Files.readString(Paths.get(codeFilePath))
        val correctFormattedCode = Files.readString(Paths.get(correctFormatFilePath))

        assertEquals(correctFormattedCode, formattedCode)
    }

    @Test
    fun `test formatter with spaces around operators`() {
        val formatter = CodeFormatter()
        val codeFilePath = "$configDirectoryPath/toFormatCases/spaceAroundOperators.txt"
        val configFilePath = "$configDirectoryPath/configurations/spaceAroundOperators.json"
        val correctFormatFilePath = "$configDirectoryPath/correctCases/spaceAroundOperators.txt"

        formatter.format(codeFilePath, configFilePath)
        val formattedCode = Files.readString(Paths.get(codeFilePath))
        val correctFormattedCode = Files.readString(Paths.get(correctFormatFilePath))

        assertEquals(correctFormattedCode, formattedCode)
    }

    @Test
    fun `test formatter with line jump before println`() {
        val formatter = CodeFormatter()
        val codeFilePath = "$configDirectoryPath/toFormatCases/lineJumpAfterSemicolon.txt"
        val configFilePath = "$configDirectoryPath/configurations/lineJumpAfterSemicolon.json"
        val correctFormatFilePath = "$configDirectoryPath/correctCases/lineJumpAfterSemicolon.txt"

        formatter.format(codeFilePath, configFilePath)
        val formattedCode = Files.readString(Paths.get(codeFilePath))
        val correctFormattedCode = Files.readString(Paths.get(correctFormatFilePath))

        assertEquals(correctFormattedCode, formattedCode)
    }

    @Test
    fun `test formatter with single space between tokens`() {
        val formatter = CodeFormatter()
        val codeFilePath = "$configDirectoryPath/toFormatCases/singleSpaceBetweenTokens.txt"
        val configFilePath = "$configDirectoryPath/configurations/singleSpaceBetweenTokens.json"
        val correctFormatFilePath = "$configDirectoryPath/correctCases/singleSpaceBetweenTokens.txt"

        formatter.format(codeFilePath, configFilePath)
        val formattedCode = Files.readString(Paths.get(codeFilePath))
        val correctFormattedCode = Files.readString(Paths.get(correctFormatFilePath))

        assertEquals(correctFormattedCode, formattedCode)
    }
}