package org.example

import AssignmentStatement
import BinaryExpression
import BooleanLiteral
import EmptyVarDeclarationStatement
import FunctionCallStatement
import IdentifierExpression
import NumberLiteral
import NumberValue
import Position
import StringLiteral
import TypeDeclarationExpression
import UnaryExpression
import VariableDeclarationStatement
import Interpreter
import interfaces.EnvProvider
import interfaces.InputProvider
import interfaces.OutPutProvider
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.Storage

class InterpreterTests {

    private lateinit var interpreter: Interpreter
    private lateinit var storage: Storage

    private val mockOutPutProvider = object : OutPutProvider {
        override fun output(message: String) {
            println(message)
        }
    }

    private val mockInputProvider = object : InputProvider {
        override fun readInput(name: String): String? {
            return "mockInput"
        }
    }

    private val mockEnvProvider = object : EnvProvider {
    }

    @BeforeEach
    fun setup() {
        interpreter = Interpreter(
            version = "1.1",
            outPutProvider = mockOutPutProvider,
            inputProvider = mockInputProvider,
            envProvider = mockEnvProvider
        )
        storage = Storage()
    }

    @Test
    fun testNumberLiteral() {
        val numberLiteral = NumberLiteral(42.0, Position(1, 1))
        val result = interpreter.interpret(numberLiteral, storage)
        assertEquals(42.0, result)
    }

    @Test
    fun testStringLiteral() {
        val stringLiteral = StringLiteral("hello", Position(1, 1))
        val result = interpreter.interpret(stringLiteral, storage)
        assertEquals("hello", result)
    }

    @Test
    fun testBooleanLiteral() {
        val booleanLiteral = BooleanLiteral(true, Position(1, 1))
        val result = interpreter.interpret(booleanLiteral, storage)
        assertEquals(true, result)
    }

    @Test
    fun testBinaryExpressionAddition() {
        val left = NumberLiteral(10.0, Position(1, 1))
        val right = NumberLiteral(5.0, Position(1, 3))
        val binaryExpression = BinaryExpression(left, "+", right, Position(1, 2))
        val result = interpreter.interpret(binaryExpression, storage)
        assertEquals(15.0, result)
    }

    @Test
    fun testBinaryExpressionSubtraction() {
        val left = NumberLiteral(10.0, Position(1, 1))
        val right = NumberLiteral(5.0, Position(1, 3))
        val binaryExpression = BinaryExpression(left, "-", right, Position(1, 2))
        val result = interpreter.interpret(binaryExpression, storage)
        assertEquals(5.0, result)
    }

    @Test
    fun testBinaryExpressionStringConcatenation() {
        val left = StringLiteral("Hello, ", Position(1, 1))
        val right = StringLiteral("World!", Position(1, 10))
        val binaryExpression = BinaryExpression(left, "+", right, Position(1, 7))
        val result = interpreter.interpret(binaryExpression, storage)
        assertEquals("Hello, World!", result)
    }

    @Test
    fun testUnaryExpression() {
        val right = NumberLiteral(5.0, Position(1, 2))
        val unaryExpression = UnaryExpression("-", right, Position(1, 1))
        val result = interpreter.interpret(unaryExpression, storage)
        assertEquals(-5.0, result)
    }

    @Test
    fun testVariableDeclarationStatement() {
        val typeDeclaration = TypeDeclarationExpression("number", Position(1, 1))
        val assignment = AssignmentStatement(IdentifierExpression("x", Position(1, 5)), "=", NumberLiteral(10.0, Position(1, 10)), Position(1, 7))
        val variableDeclaration = VariableDeclarationStatement("x", typeDeclaration, assignment, Position(1, 1))

        interpreter.interpret(variableDeclaration, storage)
        val result = storage.getFromStorage("x")
        println(result)
        assertEquals(NumberValue(10.0), result)
    }

    @Test
    fun testAssignmentStatement() {
        val assignment = AssignmentStatement(IdentifierExpression("x", Position(1, 1)), "=", NumberLiteral(20.0, Position(1, 5)), Position(1, 3))
        interpreter.interpret(assignment, storage)
        val result = storage.getFromStorage("x")
        assertEquals(NumberValue(20.0), result)
    }

    @Test
    fun testFunctionCallStatementPrintln() {
        val arguments = listOf(StringLiteral("Hello World", Position(1, 10)))
        val functionCall = FunctionCallStatement("println", arguments, emptyList(), Position(1, 1))

        val output = captureOutput {
            interpreter.interpret(functionCall, storage)
        }

        assertEquals("Hello World", output.trim())
    }

    @Test
    fun testIdentifierExpression() {
        storage.addToStorage("y", NumberValue(50.0))
        val identifier = IdentifierExpression("y", Position(1, 1))

        val result = interpreter.interpret(identifier, storage)
        assertEquals(NumberValue(50.0), result)
    }

    @Test
    fun testBinaryExpressionIntAndStringConcatenation() {
        val left = NumberLiteral(42.0, Position(1, 1))
        val right = StringLiteral(" is the answer", Position(1, 10))
        val binaryExpression = BinaryExpression(left, "+", right, Position(1, 7))
        val result = interpreter.interpret(binaryExpression, storage)
        assertEquals("42.0 is the answer", result)
    }

    @Test
    fun testUnaryExpressionInvalidOperand() {
        val right = NumberLiteral(33.2, Position(1, 2))
        val unaryExpression = UnaryExpression("+", right, Position(1, 1))

        assertThrows(IllegalArgumentException::class.java) {
            interpreter.interpret(unaryExpression, storage)
        }
    }

    @Test
    fun testUndefinedFunctionCall() {
        val arguments = listOf(NumberLiteral(10.0, Position(1, 10)))
        val functionCall = FunctionCallStatement("undefinedFunction", arguments, emptyList(), Position(1, 1))

        assertThrows(IllegalArgumentException::class.java) {
            interpreter.interpret(functionCall, storage)
        }
    }

    @Test
    fun testBinaryExpressionWithMul() {
        val left = NumberLiteral(8.0, Position(1, 1))
        val right = NumberLiteral(7.0, Position(1, 5))
        val binaryExpression = BinaryExpression(left, "*", right, Position(1, 3))
        val result = interpreter.interpret(binaryExpression, storage)
        assertEquals(56.0, result)
    }

    @Test
    fun testNestedBinaryExpressionsAdditionAndMultiplication() {
        val left = NumberLiteral(2.0, Position(1, 1))
        val right = NumberLiteral(3.0, Position(1, 5))
        val addition = BinaryExpression(left, "+", right, Position(1, 3))

        val secondRight = NumberLiteral(4.0, Position(1, 10))
        val multiplication = BinaryExpression(addition, "*", secondRight, Position(1, 7))

        val result = interpreter.interpret(multiplication, storage)
        assertEquals(20.0, result)
    }

    @Test
    fun testNestedBinaryExpressionsSubtractionAndDivision() {
        val left = NumberLiteral(10.0, Position(1, 1))
        val right = NumberLiteral(2.0, Position(1, 5))
        val division = BinaryExpression(left, "/", right, Position(1, 3))

        val secondLeft = NumberLiteral(15.0, Position(1, 10))
        val subtraction = BinaryExpression(secondLeft, "-", division, Position(1, 7))

        val result = interpreter.interpret(subtraction, storage)
        assertEquals(10.0, result)
    }

    @Test
    fun testComplexBinaryExpressions() {
        val first = BinaryExpression(NumberLiteral(5.0, Position(1, 1)), "+", NumberLiteral(10.0, Position(1, 5)), Position(1, 3))
        val second = BinaryExpression(NumberLiteral(20.0, Position(1, 10)), "-", NumberLiteral(5.0, Position(1, 15)), Position(1, 7))
        val third = BinaryExpression(first, "*", second, Position(1, 10))

        val result = interpreter.interpret(third, storage)
        assertEquals(225.0, result)
    }

    @Test
    fun testBinaryExpressionStringAndDoubleConcatenation() {
        val left = StringLiteral("The result is: ", Position(1, 1))
        val right = NumberLiteral(42.0, Position(1, 10))
        val binaryExpression = BinaryExpression(left, "+", right, Position(1, 5))

        val result = interpreter.interpret(binaryExpression, storage)
        assertEquals("The result is: 42.0", result)
    }

    @Test
    fun testEmptyVariableDeclarationStatement() {
        val typeDeclaration = TypeDeclarationExpression("number", Position(1, 1))
        val identifier = IdentifierExpression("x", Position(1, 5))
        val emptyVariableDeclaration = EmptyVarDeclarationStatement("x", identifier, typeDeclaration, Position(1, 1))

        interpreter.interpret(emptyVariableDeclaration, storage)
        val result = storage.getFromStorage("x")
        assertNull(result)
    }

    @Test
    fun testFunctionCallStatementIf() {
        val booleanLiteral = BooleanLiteral(true, Position(1, 1))
        val left = StringLiteral("The result is: ", Position(1, 1))
        val right = NumberLiteral(42.0, Position(1, 10))
        val binaryExpression = BinaryExpression(left, "+", right, Position(1, 5))
        val arguments = listOf(booleanLiteral)
        val functionCall = FunctionCallStatement("if", arguments, listOf(binaryExpression), Position(1, 1))

        interpreter.interpret(functionCall, storage)
    }

    private fun captureOutput(block: () -> Unit): String {
        val outputStream = java.io.ByteArrayOutputStream()
        val printStream = java.io.PrintStream(outputStream)
        val originalOut = System.out
        System.setOut(printStream)

        block()

        System.setOut(originalOut)
        return outputStream.toString()
    }
}