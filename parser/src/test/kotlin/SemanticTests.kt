import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import semantic.SemanticAnalyzer


class SemanticTests {

    private val semanticAnalyzer = SemanticAnalyzer()

    @Test
    fun `test valid binary expression`() {
        val numberFive = NumberLiteral(5.0)
        val numberThree = NumberLiteral(3.0)
        val additionExpression = BinaryExpression(
            left = numberFive,
            operator = "+",
            right = numberThree
        )
        assertDoesNotThrow { semanticAnalyzer.analyze(additionExpression) }
    }

    @Test
    fun `test valid variable declaration`() {
        val identifier = IdentifierExpression("x")
        val typeDeclaration = TypeDeclarationExpression("number")
        val valueExpression = NumberLiteral(10.0)
        val assignmentStatement = AssignmentStatement(
            identifier = identifier,
            value = valueExpression,
            equalOperand = "="
        )
        val variableDeclaration = VariableDeclarationStatement(
            declarator = "let",
            typeDeclarationExpression = typeDeclaration,
            assignmentStatement = assignmentStatement
        )
        assertDoesNotThrow { semanticAnalyzer.analyze(variableDeclaration) }
    }

    @Test
    fun `test println functionCall`() {
        val argument = StringLiteral("Hello, world!")
        val functionCall = FunctionCallStatement(
            functionName = "print",
            arguments = listOf(argument)
        )
        assertDoesNotThrow {semanticAnalyzer.analyze(functionCall)}
    }

    @Test
    fun `test complex binary operation`() {
        val identifierX = IdentifierExpression("x")
        val numberFive = NumberLiteral(5.0)
        val additionExpression = BinaryExpression(
            left = identifierX,
            operator = "+",
            right = numberFive
        )

        val numberTen = NumberLiteral(10.0)
        val multiplicationExpression = BinaryExpression(
            left = additionExpression,
            operator = "*",
            right = numberTen
        )

        assertDoesNotThrow {semanticAnalyzer.analyze(multiplicationExpression)}
    }

    @Test
    fun `test assignation with binary operation x = 5+3` () {
        val identifier = IdentifierExpression("x")
        val numberFive = NumberLiteral(5.0)
        val numberThree = NumberLiteral(3.0)
        val additionExpression = BinaryExpression(
            left = numberFive,
            operator = "+",
            right = numberThree
        )
        val assignmentStatement = AssignmentStatement(
            identifier = identifier,
            value = additionExpression,
            equalOperand = "="
        )
        assertDoesNotThrow { semanticAnalyzer.analyze(assignmentStatement) }
    }

}