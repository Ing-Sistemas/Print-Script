package org.example

import com.printscript.ast.*
import com.printscript.interpreter.Interpreter
import com.printscript.interpreter.interfaces.InputProvider
import com.printscript.interpreter.providers.DefaultEnvProvider
import com.printscript.interpreter.providers.DefaultInputProvider
import com.printscript.interpreter.providers.DefaultOutPutProvider
import com.printscript.interpreter.results.InterpreterFailure
import com.printscript.interpreter.results.InterpreterResultInformation
import com.printscript.interpreter.results.InterpreterSuccess
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
        val changedResult = result as InterpreterSuccess
        assertEquals(42, changedResult.getIntValue())
    }

    @Test
    fun testNumberLiteral2() {
        val numberLiteral = NumberLiteral(42.055, Position(1, 1))
        val result = interpreter1.interpret(numberLiteral, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals(42.055, changedResult.getIntValue())
    }

    @Test
    fun testStringLiteral() {
        val stringLiteral = StringLiteral("hello", Position(1, 1))
        val result = interpreter1.interpret(stringLiteral, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals("hello", changedResult.getOriginalValue())
    }

    @Test
    fun testBooleanLiteral() {
        val booleanLiteral = BooleanLiteral(true, Position(1, 1))
        val result = interpreter1.interpret(booleanLiteral, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals(true, changedResult.getOriginalValue())
    }

    @Test
    fun testBinaryExpressionAddition() {
        val left = NumberLiteral(10.0, Position(1, 1))
        val right = NumberLiteral(5.0, Position(1, 3))
        val binaryExpression = BinaryExpression(left, "+", right, Position(1, 2))
        val result = interpreter1.interpret(binaryExpression, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals(15, changedResult.getIntValue())
    }

    @Test
    fun testBinaryExpressionSubtraction() {
        val left = NumberLiteral(10.0, Position(1, 1))
        val right = NumberLiteral(5.0, Position(1, 3))
        val binaryExpression = BinaryExpression(left, "-", right, Position(1, 2))
        val result = interpreter1.interpret(binaryExpression, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals(5, changedResult.getIntValue())
    }

    @Test
    fun testBinaryExpressionStringConcatenation() {
        val left = StringLiteral("Hello, ", Position(1, 1))
        val right = StringLiteral("World!", Position(1, 10))
        val binaryExpression = BinaryExpression(left, "+", right, Position(1, 7))
        val result = interpreter1.interpret(binaryExpression, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals("Hello, World!", changedResult.getOriginalValue())
    }

    @Test
    fun testUnaryExpression() {
        val right = NumberLiteral(5.0, Position(1, 2))
        val unaryExpression = UnaryExpression("-", right, Position(1, 1))
        val result = interpreter1.interpret(unaryExpression, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals(-5, changedResult.getIntValue())
    }

    @Test
    fun testVariableDeclarationStatement() {
        val typeDeclaration = TypeDeclarationExpression("number", Position(1, 1))
        val assignment = AssignmentStatement(IdentifierExpression("x", Position(1, 5)), "=", NumberLiteral(10.0, Position(1, 10)), Position(1, 7))
        val variableDeclaration = VariableDeclarationStatement("let", typeDeclaration, assignment, Position(1, 1))

        interpreter1.interpret(variableDeclaration, storage)
        val result = storage.getFromStorage("x")
        assertEquals(NumberValue(10.0), result)
    }

    @Test
    fun testAssignmentStatement() {
        val assignment = AssignmentStatement(IdentifierExpression("x", Position(1, 1)), "=", NumberLiteral(20.0, Position(1, 5)), Position(1, 3))
        interpreter1.interpret(assignment, storage)
        val result = storage.getFromStorage("x")
        assertEquals(NumberValue(20.0), result)
    }

    @Test
    fun testVariableDeclarationStatement2() {
        val typeDeclaration = TypeDeclarationExpression("number", Position(1, 1))
        val assignment = AssignmentStatement(IdentifierExpression("b", Position(1, 5)), "=", NumberLiteral(5.0, Position(1, 10)), Position(1, 7))
        val variableDeclaration = VariableDeclarationStatement("const", typeDeclaration, assignment, Position(1, 1))

        val assignment2 = AssignmentStatement(IdentifierExpression("b", Position(1, 1)), "=", NumberLiteral(2.0, Position(1, 5)), Position(1, 3))
        interpreter1.interpret(variableDeclaration, storage)
        val result = storage.getFromStorage("b")
        interpreter1.interpret(assignment2, storage)
        assertEquals(NumberValue(5.0), result)
    }

    @Test
    fun testAssignmentStatement2() {
        val assignment = AssignmentStatement(IdentifierExpression("x", Position(1, 1)), "=", NumberLiteral(20.0, Position(1, 5)), Position(1, 3))
        interpreter1.interpret(assignment, storage)
        val result = storage.getFromStorage("x")
        assertEquals(NumberValue(20.0), result)
    }

    @Test
    fun testFunctionCallStatementPrintln() {
        val arguments = listOf(StringLiteral("Hello World", Position(1, 10)))
        val functionCall = FunctionCallStatement("println", arguments, emptyList(), Position(1, 1))
        val result = interpreter1.interpret(functionCall, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals("Hello World", changedResult.getOriginalValue())
    }

    @Test
    fun testIdentifierExpression() {
        storage.addToStorage("y", NumberValue(50.0))
        val identifier = IdentifierExpression("y", Position(1, 1))
        val result = interpreter1.interpret(identifier, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals(50, changedResult.getIntValue())
    }

    @Test
    fun testBinaryExpressionIntAndStringConcatenation() {
        val left = NumberLiteral(42.0, Position(1, 1))
        val right = StringLiteral(" is the answer", Position(1, 10))
        val binaryExpression = BinaryExpression(left, "+", right, Position(1, 7))
        val result = interpreter1.interpret(binaryExpression, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals("42 is the answer", changedResult.getIntValue())
    }

    @Test
    fun testUnaryExpressionInvalidOperand() {
        val right = NumberLiteral(33.2, Position(1, 2))
        val unaryExpression = UnaryExpression("+", right, Position(1, 1))
        val result = interpreter1.interpret(unaryExpression, storage) as InterpreterFailure
        val assertion = InterpreterFailure("Unary operator + not supported")
        assertEquals(assertion.getErrorMessage(), result.getErrorMessage())
    }

    @Test
    fun testBinaryExpressionWithMul() {
        val left = NumberLiteral(3.1, Position(1, 1))
        val right = NumberLiteral(2.0, Position(1, 5))
        val binaryExpression = BinaryExpression(left, "/", right, Position(1, 3))
        val result = interpreter1.interpret(binaryExpression, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals(1.55, changedResult.getIntValue())
    }

    @Test
    fun testNestedBinaryExpressionsAdditionAndMultiplication() {
        val left = NumberLiteral(5.0, Position(1, 1))
        val right = NumberLiteral(5.0, Position(1, 5))
        val addition = BinaryExpression(left, "*", right, Position(1, 3))

        val secondRight = NumberLiteral(8.0, Position(1, 10))
        val multiplication = BinaryExpression(addition, "-", secondRight, Position(1, 7))

        val result = interpreter1.interpret(multiplication, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals(17, changedResult.getIntValue())
    }

    @Test
    fun testNestedBinaryExpressionsSubtractionAndDivision() {
        val left = NumberLiteral(10.0, Position(1, 1))
        val right = NumberLiteral(2.0, Position(1, 5))
        val division = BinaryExpression(left, "/", right, Position(1, 3))

        val secondLeft = NumberLiteral(15.0, Position(1, 10))
        val subtraction = BinaryExpression(secondLeft, "-", division, Position(1, 7))

        val result = interpreter1.interpret(subtraction, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals(10, changedResult.getIntValue())
    }

    @Test
    fun testComplexBinaryExpressions() {
        val first = BinaryExpression(NumberLiteral(5.0, Position(1, 1)), "+", NumberLiteral(10.0, Position(1, 5)), Position(1, 3))
        val second = BinaryExpression(NumberLiteral(20.0, Position(1, 10)), "-", NumberLiteral(5.0, Position(1, 15)), Position(1, 7))
        val third = BinaryExpression(first, "*", second, Position(1, 10))

        val result = interpreter1.interpret(third, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals(225, changedResult.getIntValue())
    }

    @Test
    fun testBinaryExpressionStringAndDoubleConcatenation() {
        val left = StringLiteral("The result is: ", Position(1, 1))
        val right = NumberLiteral(42.0, Position(1, 10))
        val binaryExpression = BinaryExpression(left, "+", right, Position(1, 5))

        val result = interpreter1.interpret(binaryExpression, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals("The result is: 42", changedResult.getOriginalValue())
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
        val changedResult = result as InterpreterSuccess
        assertEquals("The result is: 42", changedResult.getOriginalValue())
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

        val leftElse = StringLiteral("Else block executed ", Position(2, 1))
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

        val changedResult = result as InterpreterSuccess
        assertEquals("Else block executed 100", changedResult.getOriginalValue())
    }

    @Test
    fun testIfStatementWithLiteral() {
        val booleanLiteral = BooleanLiteral(true, Position(1, 1))
        val literalToExecute = StringLiteral("Executed Literal", Position(1, 5))

        val ifStatement = IfStatement(
            condition = booleanLiteral,
            thenBlock = listOf(literalToExecute),
            elseBlock = null,
            position = Position(1, 1),
        )

        val result = interpreter1.interpret(ifStatement, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals("Executed Literal", changedResult.getOriginalValue())
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
            position = Position(1, 1),
        )

        val result = interpreter1.interpret(ifStatement, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals(30, changedResult.getIntValue())
    }

    @Test
    fun testElseStatementWithLiteral() {
        val booleanLiteral = BooleanLiteral(false, Position(1, 1))
        val literalToExecute = StringLiteral("Executed Literal in Else", Position(1, 5))

        val ifStatement = IfStatement(
            condition = booleanLiteral,
            thenBlock = emptyList(),
            elseBlock = listOf(literalToExecute),
            position = Position(1, 1),
        )

        val result = interpreter1.interpret(ifStatement, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals("Executed Literal in Else", changedResult.getOriginalValue())
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
            position = Position(1, 1),
        )

        val result = interpreter1.interpret(ifStatement, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals(2, changedResult.getIntValue())
    }

    @Test
    fun passByThings() {
        val defaultEnvProv = DefaultEnvProvider()
        defaultEnvProv.getEnv("x")
        val defaultInputProv = DefaultInputProvider()
        defaultInputProv.readInput("x")
        interpreter1.interpret(ReadEnvNode("x", Position(1, 1)), storage)
    }

    @Test
    fun testStringToInt() {
        val newValue = StringValue("42")
        val result = InterpreterSuccess(newValue)
        assertEquals(42, result.getIntValue())
    }

    class TestInputProv(var input: String? = null) : InputProvider {
        override fun readInput(name: String): String? {
            return input
        }
    }

    @Test
    fun testReadInput() {
        val inputProvider = TestInputProv("Felipe")
        val interpreterExample = Interpreter(
            outPutProvider,
            inputProvider,
            envProvider,
        )

        val readInput = ReadInputNode("Name:", Position(1, 1))
        val typeDeclaration = TypeDeclarationExpression("string", Position(1, 1))
        val assignment = AssignmentStatement(IdentifierExpression("name", Position(1, 5)), "=", readInput, Position(1, 7))
        val variableDeclaration = VariableDeclarationStatement("const", typeDeclaration, assignment, Position(1, 77))
        val result2 = interpreterExample.interpret(variableDeclaration, storage)
        result2 as InterpreterResultInformation
        val result = storage.getFromStorage("name")
        assertEquals(InterpreterResultInformation::class.java, result2::class.java)
        assertEquals(StringValue("Felipe"), result)
    }
}