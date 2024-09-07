package org.example

import AssignmentStatement
import BinaryExpression
import FunctionCallStatement
import IdentifierExpression
import NumberLiteral
import StringLiteral
import TypeDeclarationExpression
import UnaryExpression
import VariableDeclarationStatement
import interpreters.Interpreter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.Storage

class InterpreterTests {

    private lateinit var interpreter: Interpreter
    private lateinit var storage: Storage

    @BeforeEach
    fun setup() {
        interpreter = Interpreter()
        storage = Storage()
    }

    @Test
    fun testNumberLiteral() {
        val numberLiteral = NumberLiteral(42.0)
        val result = interpreter.interpret(numberLiteral)
        assertEquals(42, result)
    }

    @Test
    fun testStringLiteral() {
        val stringLiteral = StringLiteral("hello")
        val result = interpreter.interpret(stringLiteral)
        assertEquals("hello", result)
    }

    @Test
    fun testBinaryExpressionAddition() {
        val left = NumberLiteral(10.0)
        val right = NumberLiteral(5.0)
        val binaryExpression = BinaryExpression(left, "+", right)
        val result = interpreter.interpret(binaryExpression)
        assertEquals(15, result)
    }

    @Test
    fun testBinaryExpressionSubtraction() {
        val left = NumberLiteral(10.0)
        val right = NumberLiteral(5.0)
        val binaryExpression = BinaryExpression(left, "-", right)
        val result = interpreter.interpret(binaryExpression)
        assertEquals(5, result)
    }

    @Test
    fun testBinaryExpressionStringConcatenation() {
        val left = StringLiteral("Hello, ")
        val right = StringLiteral("World!")
        val binaryExpression = BinaryExpression(left, "+", right)
        val result = interpreter.interpret(binaryExpression)
        assertEquals("Hello, World!", result)
    }

    @Test
    fun testUnaryExpression() {
        val right = NumberLiteral(5.0)
        val unaryExpression = UnaryExpression("-", right)
        val result = interpreter.interpret(unaryExpression)
        assertEquals(-5.0, result)
    }

    @Test
    fun testVariableDeclarationStatement() {
        val typeDeclaration = TypeDeclarationExpression("int")
        val assignment = AssignmentStatement(IdentifierExpression("x"), NumberLiteral(10.0), "=")
        val variableDeclaration = VariableDeclarationStatement("x", typeDeclaration, assignment)

        interpreter.interpret(variableDeclaration)
        val result = storage.getFromStorage("x")
        assertEquals(10, result)
    }

    @Test
    fun testAssignmentStatement() {
        val assignment = AssignmentStatement(IdentifierExpression("x"), NumberLiteral(20.0), "=")

        interpreter.interpret(assignment)
        val result = storage.getFromStorage("x")
        assertEquals(20, result)
    }

    @Test
    fun testFunctionCallStatementPrintln() {
        val arguments = listOf(StringLiteral("Hello World"))
        val functionCall = FunctionCallStatement("println", arguments)

        val output = captureOutput {
            interpreter.interpret(functionCall)
        }

        assertEquals("Hello World", output.trim())
    }

    @Test
    fun testIdentifierExpression() {
        storage.addToStorage("y", 50)
        val identifier = IdentifierExpression("y")

        val result = interpreter.interpret(identifier)
        assertEquals(50, result)
    }

    @Test
    fun testBinaryExpressionIntAndStringConcatenation() {
        val left = NumberLiteral(42.0)
        val right = StringLiteral(" is the answer")
        val binaryExpression = BinaryExpression(left, "+", right)
        val result = interpreter.interpret(binaryExpression)
        assertEquals("42 is the answer", result)
    }

    @Test
    fun testUnaryExpressionInvalidOperand() {
        val right = NumberLiteral(33.2)
        val unaryExpression = UnaryExpression("+", right)

        assertThrows(IllegalArgumentException::class.java) {
            interpreter.interpret(unaryExpression)
        }
    }

    @Test
    fun testUndefinedFunctionCall() {
        val arguments = listOf(NumberLiteral(10.0))
        val functionCall = FunctionCallStatement("undefinedFunction", arguments)

        assertThrows(IllegalArgumentException::class.java) {
            interpreter.interpret(functionCall)
        }
    }

    @Test
    fun testBinaryExpressionWithMul() {
        val left = NumberLiteral(8.0)
        val right = NumberLiteral(7.0)
        val binaryExpression = BinaryExpression(left, "*", right)
        val result = interpreter.interpret(binaryExpression)
        assertEquals(56.0, result)
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