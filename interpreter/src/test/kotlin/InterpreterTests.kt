package org.example

import com.printscript.ast.*
import com.printscript.interpreter.Interpreter
import com.printscript.interpreter.providers.DefaultEnvProvider
import com.printscript.interpreter.providers.DefaultInputProvider
import com.printscript.interpreter.providers.DefaultOutPutProvider
import com.printscript.interpreter.utils.Storage
import com.printscript.token.Position
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class InterpreterTests {

    private lateinit var interpreter1: Interpreter
    private lateinit var interpreter0: Interpreter
    private lateinit var storage: Storage

    private val outPutProvider: DefaultOutPutProvider = DefaultOutPutProvider()
    private val inputProvider: DefaultInputProvider = DefaultInputProvider()
    private val envProvider: DefaultEnvProvider = DefaultEnvProvider()

    @BeforeEach
    fun setup() {
        interpreter1 = Interpreter(
            outPutProvider,
            inputProvider,
            envProvider,
        )
        storage = Storage()
        interpreter0 = Interpreter(
            outPutProvider,
            inputProvider,
            envProvider,
        )
    }

    @Test
    fun testNumberLiteral() {
        val numberLiteral = NumberLiteral(42.0, Position(1, 1))
        val result = interpreter1.interpret(numberLiteral, storage)
        assert(result.isEmpty())
    }

    @Test
    fun testStringLiteral() {
        val stringLiteral = StringLiteral("hello", Position(1, 1))
        val result = interpreter1.interpret(stringLiteral, storage)
        assert(result.isEmpty())
    }

    @Test
    fun testBooleanLiteral() {
        val booleanLiteral = BooleanLiteral(true, Position(1, 1))
        val result = interpreter1.interpret(booleanLiteral, storage)
        assert(result.isEmpty())
    }

    @Test
    fun testBinaryExpressionAddition() {
        val left = NumberLiteral(10.0, Position(1, 1))
        val right = NumberLiteral(5.0, Position(1, 3))
        val binaryExpression = BinaryExpression(left, "+", right, Position(1, 2))
        val result = interpreter1.interpret(binaryExpression, storage)
        assert(result.isEmpty())
    }

    @Test
    fun testBinaryExpressionSubtraction() {
        val left = NumberLiteral(10.0, Position(1, 1))
        val right = NumberLiteral(5.0, Position(1, 3))
        val binaryExpression = BinaryExpression(left, "-", right, Position(1, 2))
        val result = interpreter1.interpret(binaryExpression, storage)
        assert(result.isEmpty())
    }

    @Test
    fun testBinaryExpressionStringConcatenation() {
        val left = StringLiteral("Hello, ", Position(1, 1))
        val right = StringLiteral("World!", Position(1, 10))
        val binaryExpression = BinaryExpression(left, "+", right, Position(1, 7))
        val result = interpreter1.interpret(binaryExpression, storage)
        assert(result.isEmpty())
    }

    @Test
    fun testUnaryExpression() {
        val right = NumberLiteral(5.0, Position(1, 2))
        val unaryExpression = UnaryExpression("-", right, Position(1, 1))
        val result = interpreter1.interpret(unaryExpression, storage)
        assert(result.isEmpty())
    }

    @Test
    fun testVariableDeclarationStatement() {
        val typeDeclaration = TypeDeclarationExpression("number", Position(1, 1))
        val assignment = AssignmentStatement(IdentifierExpression("x", Position(1, 5)), "=", NumberLiteral(10.0, Position(1, 10)), Position(1, 7))
        val variableDeclaration = VariableDeclarationStatement("let", typeDeclaration, assignment, Position(1, 1))

        val intResult = interpreter1.interpret(variableDeclaration, storage)
        val result = storage.getFromStorage("x")
        assertEquals(NumberValue(10.0), result)
        assert(intResult.isEmpty())
    }

    @Test
    fun testAssignmentStatement() {
        val assignment = AssignmentStatement(IdentifierExpression("x", Position(1, 1)), "=", NumberLiteral(20.0, Position(1, 5)), Position(1, 3))
        val intResult = interpreter1.interpret(assignment, storage)
        val result = storage.getFromStorage("x")
        assertEquals(NumberValue(20.0), result)
        assert(intResult.isEmpty())
    }

    @Test
    fun testFunctionCallStatementPrintln() {
        val arguments = listOf(StringLiteral("Hello World", Position(1, 10)))
        val functionCall = FunctionCallStatement("println", arguments, emptyList(), Position(1, 1))
        val result = interpreter1.interpret(functionCall, storage)
        assert(result.isEmpty())
    }

    @Test
    fun testIdentifierExpression() {
        storage.addToStorage("y", NumberValue(50.0))
        val identifier = IdentifierExpression("y", Position(1, 1))
        val result = interpreter1.interpret(identifier, storage)
        assert(result.isEmpty())
    }

    @Test
    fun testBinaryExpressionIntAndStringConcatenation() {
        val left = NumberLiteral(42.0, Position(1, 1))
        val right = StringLiteral(" is the answer", Position(1, 10))
        val binaryExpression = BinaryExpression(left, "+", right, Position(1, 7))
        val result = interpreter1.interpret(binaryExpression, storage)
        assert(result.isEmpty())
    }

    @Test
    fun testUnaryExpressionInvalidOperand() {
        val right = NumberLiteral(33.2, Position(1, 2))
        val unaryExpression = UnaryExpression("+", right, Position(1, 1))
        val result = interpreter1.interpret(unaryExpression, storage)
        println(result)
        assert(result.isNotEmpty())
    }

    @Test
    fun testBinaryExpressionWithMul() {
        val left = NumberLiteral(8.0, Position(1, 1))
        val right = NumberLiteral(7.0, Position(1, 5))
        val binaryExpression = BinaryExpression(left, "*", right, Position(1, 3))
        val result = interpreter1.interpret(binaryExpression, storage)
        assert(result.isEmpty())
    }

    @Test
    fun testNestedBinaryExpressionsAdditionAndMultiplication() {
        val left = NumberLiteral(2.0, Position(1, 1))
        val right = NumberLiteral(3.0, Position(1, 5))
        val addition = BinaryExpression(left, "+", right, Position(1, 3))

        val secondRight = NumberLiteral(4.0, Position(1, 10))
        val multiplication = BinaryExpression(addition, "*", secondRight, Position(1, 7))

        val result = interpreter1.interpret(multiplication, storage)
        assert(result.isEmpty())
    }

    @Test
    fun testNestedBinaryExpressionsSubtractionAndDivision() {
        val left = NumberLiteral(10.0, Position(1, 1))
        val right = NumberLiteral(2.0, Position(1, 5))
        val division = BinaryExpression(left, "/", right, Position(1, 3))

        val secondLeft = NumberLiteral(15.0, Position(1, 10))
        val subtraction = BinaryExpression(secondLeft, "-", division, Position(1, 7))

        val result = interpreter1.interpret(subtraction, storage)
        assert(result.isEmpty())
    }

    @Test
    fun testComplexBinaryExpressions() {
        val first = BinaryExpression(NumberLiteral(5.0, Position(1, 1)), "+", NumberLiteral(10.0, Position(1, 5)), Position(1, 3))
        val second = BinaryExpression(NumberLiteral(20.0, Position(1, 10)), "-", NumberLiteral(5.0, Position(1, 15)), Position(1, 7))
        val third = BinaryExpression(first, "*", second, Position(1, 10))

        val result = interpreter1.interpret(third, storage)
        assert(result.isEmpty())
    }

    @Test
    fun testBinaryExpressionStringAndDoubleConcatenation() {
        val left = StringLiteral("The result is: ", Position(1, 1))
        val right = NumberLiteral(42.0, Position(1, 10))
        val binaryExpression = BinaryExpression(left, "+", right, Position(1, 5))

        val result = interpreter1.interpret(binaryExpression, storage)
        assert(result.isEmpty())
    }

    @Test
    fun testEmptyVariableDeclarationStatement() {
        val typeDeclaration = TypeDeclarationExpression("number", Position(1, 1))
        val identifier = IdentifierExpression("x", Position(1, 5))
        val emptyVariableDeclaration = EmptyVarDeclarationStatement("x", identifier, typeDeclaration, Position(1, 1))

        interpreter1.interpret(emptyVariableDeclaration, storage)
        val result = storage.getFromStorage("x")
        assertNull(result)
    }

    @Test
    fun testFunctionCallStatementIf() {
        val booleanLiteral = BooleanLiteral(true, Position(1, 1))
        val left = StringLiteral("The result is: ", Position(1, 1))
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

        val result = interpreter1.interpret(ifStatement, storage)
        assert(result.isEmpty())
    }

    @Test
    fun testElseStatement() {
        val booleanLiteral = BooleanLiteral(false, Position(1, 1))

        val leftThen = StringLiteral("This should not be printed", Position(1, 1))
        val rightThen = NumberLiteral(0.0, Position(1, 30))
        val binaryExpressionThen = BinaryExpression(leftThen, "+", rightThen, Position(1, 15))
        val functionCallThenBlock = FunctionCallStatement(
            functionName = "println",
            arguments = listOf(binaryExpressionThen),
            block = null,
            position = Position(1, 1),
        )

        val leftElse = StringLiteral("Else block executed", Position(2, 1))
        val rightElse = NumberLiteral(100.0, Position(2, 30))
        val binaryExpressionElse = BinaryExpression(leftElse, "+", rightElse, Position(2, 15))
        val functionCallElseBlock = FunctionCallStatement(
            functionName = "println",
            arguments = listOf(binaryExpressionElse),
            block = null,
            position = Position(2, 1),
        )

        val ifStatement = IfStatement(
            condition = booleanLiteral,
            thenBlock = listOf(functionCallThenBlock),
            elseBlock = listOf(functionCallElseBlock),
            position = Position(1, 1),
        )

        val result = interpreter1.interpret(ifStatement, storage)

        assert(result.isEmpty())
    }

    @Test
    fun testIfStatementWithLiteral() {
        val booleanLiteral = BooleanLiteral(true, Position(1, 1))
        val literalToExecute = StringLiteral("Executed Literal", Position(1, 5))

        val ifStatement = IfStatement(
            condition = booleanLiteral,
            thenBlock = listOf(literalToExecute),
            elseBlock = null,
            position = Position(1, 1)
        )

        val result = interpreter1.interpret(ifStatement, storage)
        assert(result.isEmpty())
    }

    @Test
    fun testIfStatementWithExpression() {
        val booleanLiteral = BooleanLiteral(true, Position(1, 1))
        val left = NumberLiteral(10.0, Position(1, 5))
        val right = NumberLiteral(20.0, Position(1, 10))
        val binaryExpression = BinaryExpression(left, "+", right, Position(1, 7))

        val ifStatement = IfStatement(
            condition = booleanLiteral,
            thenBlock = listOf(binaryExpression),
            elseBlock = null,
            position = Position(1, 1)
        )

        val result = interpreter1.interpret(ifStatement, storage)
        assert(result.isEmpty())
    }

    @Test
    fun testElseStatementWithLiteral() {
        val booleanLiteral = BooleanLiteral(false, Position(1, 1))
        val literalToExecute = StringLiteral("Executed Literal in Else", Position(1, 5))

        val ifStatement = IfStatement(
            condition = booleanLiteral,
            thenBlock = emptyList(),
            elseBlock = listOf(literalToExecute),
            position = Position(1, 1)
        )

        val result = interpreter1.interpret(ifStatement, storage)
        assert(result.isEmpty())
    }

    @Test
    fun testElseStatementWithExpression() {
        val booleanLiteral = BooleanLiteral(false, Position(1, 1))
        val left = NumberLiteral(5.0, Position(1, 5))
        val right = NumberLiteral(3.0, Position(1, 10))
        val binaryExpression = BinaryExpression(left, "-", right, Position(1, 7))

        val ifStatement = IfStatement(
            condition = booleanLiteral,
            thenBlock = emptyList(),
            elseBlock = listOf(binaryExpression),
            position = Position(1, 1)
        )

        val result = interpreter1.interpret(ifStatement, storage)
        assert(result.isEmpty())
    }

    @Test
    fun passByThings() {
        val defaultEnvProv = DefaultEnvProvider()
        defaultEnvProv.getEnv("x")
        val defaultInputProv = DefaultInputProvider()
        defaultInputProv.readInput("x")
    }
}