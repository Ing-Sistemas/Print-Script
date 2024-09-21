import org.example.CodeFormatter
import org.example.config.FormatterConfig
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FormatterTest {

    @Test
    fun `test variable declaration of number`() {
        val config = FormatterConfig(
            true,
            true,
            true,
            1
        )
        val assignment = AssignmentStatement(
            IdentifierExpression("a", Position(6,1)),
            "=",
            NumberLiteral(5.0, Position(7,1)),
            Position(9,1)
        )
        val ast = VariableDeclarationStatement(
            "let",
            TypeDeclarationExpression("number", Position(4, 1)),
            assignment,
            Position(0,1)
        )
        val formatted = CodeFormatter().format(ast, config)
        assertEquals("let a: number = 5.0; \n", formatted)
    }
}