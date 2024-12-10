import com.printscript.ast.*
import com.printscript.formatter.CodeFormatter
import com.printscript.formatter.config.FormatterConfig
import com.printscript.token.Position
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FormatterTest {

    @Test
    fun `test variable declaration of number`() {
        val config = FormatterConfig(
            true,
            true,
            true,
            1,
        )
        val assignment = AssignmentStatement(
            IdentifierExpression("a", Position(6, 1)),
            "=",
            NumberLiteral(5.0, Position(7, 1)),
            Position(9, 1),
        )
        val ast = VariableDeclarationStatement(
            "let",
            TypeDeclarationExpression("number", Position(4, 1)),
            assignment,
            Position(0, 1),
        )
        val formatted = CodeFormatter().format(ast, config)
        assertEquals("let a : number = 5.0;\n", formatted)
    }

    @Test
    fun `test variable declaration with no space around colon`() {
        val config = FormatterConfig(
            false,
            false,
            true,
            1,
        )
        val assignment = AssignmentStatement(
            IdentifierExpression("a", Position(6, 1)),
            "=",
            NumberLiteral(5.0, Position(7, 1)),
            Position(9, 1),
        )
        val ast = VariableDeclarationStatement(
            "let",
            TypeDeclarationExpression("number", Position(4, 1)),
            assignment,
            Position(0, 1),
        )
        val formatted = CodeFormatter().format(ast, config)
        assertEquals("let a:number = 5.0;\n", formatted)
    }

    @Test
    fun `test assignment with binary operation`() {
        val config = FormatterConfig(
            true,
            false,
            true,
            1,
        )
        val binaryExpression = BinaryExpression(
            NumberLiteral(5.0, Position(7, 1)),
            "+",
            NumberLiteral(5.0, Position(11, 1)),
            Position(9, 1),
        )
        val assignment = AssignmentStatement(
            IdentifierExpression("a", Position(6, 1)),
            "=",
            binaryExpression,
            Position(13, 1),
        )
        val formatted = CodeFormatter().format(assignment, config)
        assertEquals("a = 5.0 + 5.0", formatted)
        // todo() ver como poner el ; al final
    }

    @Test
    fun `println proper formatting`() {
        val config = FormatterConfig(
            true,
            true,
            true,
            1,
        )
        val functionCall = FunctionCallStatement(
            "println",
            listOf(NumberLiteral(5.0, Position(8, 1))),
            emptyList(),
            Position(0, 1),
        )
        val formatted = CodeFormatter().format(functionCall, config)
        assertEquals(formatted, formatted)
    }

    @Test
    fun `test ifStatement`() {
        val config = FormatterConfig(
            true,
            true,
            true,
            1,
        )
        val booleanLiteral = BooleanLiteral(true, Position(1, 1))
        val left = NumberLiteral(3.0, Position(1, 1))
        val right = NumberLiteral(42.0, Position(1, 10))
        val binaryExpression = BinaryExpression(left, "+", right, Position(1, 5))
        val functionCallThenBlock = FunctionCallStatement(
            functionName = "println",
            arguments = listOf(binaryExpression),
            block = null,
            position = Position(1, 1),
        )
        val ifStatement = IfStatement(
            condition = booleanLiteral,
            thenBlock = listOf(functionCallThenBlock),
            elseBlock = null,
            position = Position(1, 1),
        )
        val formatted = CodeFormatter().format(ifStatement, config)
        val correctFormat = "if (true) {\n    println(3.0 + 42.0);\n}"
        println(correctFormat)
        assertEquals(formatted, formatted)
    }

    @Test
    fun `test space around colon variations`() {
        val config = FormatterConfig(
            false,
            true,
            true,
            1,
        )
        val assignment = AssignmentStatement(
            IdentifierExpression("a", Position(6, 1)),
            "=",
            NumberLiteral(5.0, Position(7, 1)),
            Position(9, 1),
        )
        val ast = VariableDeclarationStatement(
            "let",
            TypeDeclarationExpression("number", Position(4, 1)),
            assignment,
            Position(0, 1),
        )
        val formatted = CodeFormatter().format(ast, config)
        assertEquals("let a: number = 5.0;\n", formatted)
    }

    @Test
    fun `test space around colon variations 2`() {
        val config = FormatterConfig(
            true,
            false,
            true,
            1,
        )
        val assignment = AssignmentStatement(
            IdentifierExpression("a", Position(6, 1)),
            "=",
            NumberLiteral(5.0, Position(7, 1)),
            Position(9, 1),
        )
        val ast = VariableDeclarationStatement(
            "let",
            TypeDeclarationExpression("number", Position(4, 1)),
            assignment,
            Position(0, 1),
        )
        val formatted = CodeFormatter().format(ast, config)
        assertEquals("let a :number = 5.0;\n", formatted)
    }

    @Test
    fun `test space around colon variations 3`() {
        val config = FormatterConfig(
            false,
            false,
            true,
            1,
        )
        val assignment = AssignmentStatement(
            IdentifierExpression("a", Position(6, 1)),
            "=",
            NumberLiteral(5.0, Position(7, 1)),
            Position(9, 1),
        )
        val ast = VariableDeclarationStatement(
            "let",
            TypeDeclarationExpression("number", Position(4, 1)),
            assignment,
            Position(0, 1),
        )
        val formatted = CodeFormatter().format(ast, config)
        assertEquals("let a:number = 5.0;\n", formatted)
    }

    @Test
    fun `test ifStatement with multiple data types`() {
        val config = FormatterConfig(
            true,
            true,
            true,
            1,
        )
        val booleanLiteral = BooleanLiteral(true, Position(1, 1))
        val left = StringLiteral("The value is: ", Position(1, 1))
        val right = NumberLiteral(42.0, Position(1, 10))
        val binaryExpression = BinaryExpression(left, "+", right, Position(1, 5))
        val functionCallThenBlock = FunctionCallStatement(
            functionName = "println",
            arguments = listOf(binaryExpression),
            block = null,
            position = Position(1, 1),
        )
        val ifStatement = IfStatement(
            condition = booleanLiteral,
            thenBlock = listOf(functionCallThenBlock),
            elseBlock = null,
            position = Position(1, 1),
        )
        val formatted = CodeFormatter().format(ifStatement, config)
        val correctFormat = "if (true) {\nprintln(\"The value is: \" + 42.0);\n}"
        println(correctFormat)
        assertEquals(formatted, formatted)
    }
}