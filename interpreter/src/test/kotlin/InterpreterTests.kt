package org.example

import AssignmentStatement
import BinaryExpression
import BooleanLiteral
import EmptyVarDeclarationStatement
import FunctionCallStatement
import IdentifierExpression
import IfStatement
import Interpreter
import NumberLiteral
import NumberValue
import Position
import StringLiteral
import TypeDeclarationExpression
import UnaryExpression
import VariableDeclarationStatement
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import providers.DefaultEnvProvider
import providers.DefaultInputProvider
import providers.DefaultOutPutProvider
import results.InterpreterFailure
import results.InterpreterSuccess
import utils.*

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
            version = "1.1",
            outPutProvider,
            inputProvider,
            envProvider,
        )
        storage = Storage()
        interpreter0 = Interpreter(
            version = "1.0",
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
        assertEquals(42.0, changedResult.getOriginalValue())
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
        assertEquals(15.0, changedResult.getOriginalValue())
    }

    @Test
    fun testBinaryExpressionSubtraction() {
        val left = NumberLiteral(10.0, Position(1, 1))
        val right = NumberLiteral(5.0, Position(1, 3))
        val binaryExpression = BinaryExpression(left, "-", right, Position(1, 2))
        val result = interpreter1.interpret(binaryExpression, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals(5.0, changedResult.getOriginalValue())
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
        assertEquals(-5.0, changedResult.getOriginalValue())
    }

    @Test
    fun testVariableDeclarationStatement() {
        val typeDeclaration = TypeDeclarationExpression("number", Position(1, 1))
        val assignment = AssignmentStatement(IdentifierExpression("x", Position(1, 5)), "=", NumberLiteral(10.0, Position(1, 10)), Position(1, 7))
        val variableDeclaration = VariableDeclarationStatement("x", typeDeclaration, assignment, Position(1, 1))

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
        println(changedResult.getSuccess())
        assertEquals(50.0, changedResult.getOriginalValue())
    }

    @Test
    fun testBinaryExpressionIntAndStringConcatenation() {
        val left = NumberLiteral(42.0, Position(1, 1))
        val right = StringLiteral(" is the answer", Position(1, 10))
        val binaryExpression = BinaryExpression(left, "+", right, Position(1, 7))
        val result = interpreter1.interpret(binaryExpression, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals("42.0 is the answer", changedResult.getOriginalValue())
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
    fun testUndefinedFunctionCall() {
        val arguments = listOf(NumberLiteral(10.0, Position(1, 10)))
        val functionCall = FunctionCallStatement("undefinedFunction", arguments, emptyList(), Position(1, 1))
        val assertion = InterpreterFailure("Function undefinedFunction is not defined in 1.1")
        val result = interpreter1.interpret(functionCall, storage) as InterpreterFailure
        assertEquals(assertion.getErrorMessage(), result.getErrorMessage())
    }

    @Test
    fun testBinaryExpressionWithMul() {
        val left = NumberLiteral(8.0, Position(1, 1))
        val right = NumberLiteral(7.0, Position(1, 5))
        val binaryExpression = BinaryExpression(left, "*", right, Position(1, 3))
        val result = interpreter1.interpret(binaryExpression, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals(56.0, changedResult.getOriginalValue())
    }

    @Test
    fun testNestedBinaryExpressionsAdditionAndMultiplication() {
        val left = NumberLiteral(2.0, Position(1, 1))
        val right = NumberLiteral(3.0, Position(1, 5))
        val addition = BinaryExpression(left, "+", right, Position(1, 3))

        val secondRight = NumberLiteral(4.0, Position(1, 10))
        val multiplication = BinaryExpression(addition, "*", secondRight, Position(1, 7))

        val result = interpreter1.interpret(multiplication, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals(20.0, changedResult.getOriginalValue())
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
        assertEquals(10.0, changedResult.getOriginalValue())
    }

    @Test
    fun testComplexBinaryExpressions() {
        val first = BinaryExpression(NumberLiteral(5.0, Position(1, 1)), "+", NumberLiteral(10.0, Position(1, 5)), Position(1, 3))
        val second = BinaryExpression(NumberLiteral(20.0, Position(1, 10)), "-", NumberLiteral(5.0, Position(1, 15)), Position(1, 7))
        val third = BinaryExpression(first, "*", second, Position(1, 10))

        val result = interpreter1.interpret(third, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals(225.0, changedResult.getOriginalValue())
    }

    @Test
    fun testBinaryExpressionStringAndDoubleConcatenation() {
        val left = StringLiteral("The result is: ", Position(1, 1))
        val right = NumberLiteral(42.0, Position(1, 10))
        val binaryExpression = BinaryExpression(left, "+", right, Position(1, 5))

        val result = interpreter1.interpret(binaryExpression, storage)
        val changedResult = result as InterpreterSuccess
        assertEquals("The result is: 42.0", changedResult.getOriginalValue())
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
        assertEquals("The result is: 42.0", changedResult.getOriginalValue())
    }

    @Test
    fun testFunctionCallStatementIfInNotSupported() {
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

        val result = interpreter0.interpret(ifStatement, storage)
        val changedResult = result as InterpreterFailure
        assertEquals("If is not supported in version: 1.0", changedResult.getErrorMessage())
    }
}