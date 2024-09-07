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

    // Test para literales numéricos
    @Test
    fun testNumberLiteral() {
        val numberLiteral = NumberLiteral(42.0)
        val result = interpreter.interpret(numberLiteral)
        assertEquals(42, result)
    }

    // Test para literales de string
    @Test
    fun testStringLiteral() {
        val stringLiteral = StringLiteral("hello")
        val result = interpreter.interpret(stringLiteral)
        assertEquals("hello", result)
    }

    // Test para BinaryExpression suma de enteros
    @Test
    fun testBinaryExpressionAddition() {
        val left = NumberLiteral(10.0)
        val right = NumberLiteral(5.0)
        val binaryExpression = BinaryExpression(left, "+", right)
        val result = interpreter.interpret(binaryExpression)
        assertEquals(15, result)
    }

    // Test para BinaryExpression resta de enteros
    @Test
    fun testBinaryExpressionSubtraction() {
        val left = NumberLiteral(10.0)
        val right = NumberLiteral(5.0)
        val binaryExpression = BinaryExpression(left, "-", right)
        val result = interpreter.interpret(binaryExpression)
        assertEquals(5, result)
    }

    // Test para BinaryExpression con strings
    @Test
    fun testBinaryExpressionStringConcatenation() {
        val left = StringLiteral("Hello, ")
        val right = StringLiteral("World!")
        val binaryExpression = BinaryExpression(left, "+", right)
        val result = interpreter.interpret(binaryExpression)
        assertEquals("Hello, World!", result)
    }

    // Test para UnaryExpression (negación)
    @Test
    fun testUnaryExpression() {
        val right = NumberLiteral(5.0)
        val unaryExpression = UnaryExpression("-", right)
        val result = interpreter.interpret(unaryExpression)
        assertEquals(-5, result)
    }

    // Test para declarar una variable y verificar su almacenamiento
    @Test
    fun testVariableDeclarationStatement() {
        val typeDeclaration = TypeDeclarationExpression("int")
        val assignment = AssignmentStatement(IdentifierExpression("x"), NumberLiteral(10.0), "=")
        val variableDeclaration = VariableDeclarationStatement("x", typeDeclaration, assignment)

        interpreter.interpret(variableDeclaration)
        val result = storage.getFromStorage("x")
        assertEquals(10, result)
    }

    // Test para la asignación de una variable
    @Test
    fun testAssignmentStatement() {
        val assignment = AssignmentStatement(IdentifierExpression("x"), NumberLiteral(20.0), "=")

        interpreter.interpret(assignment)
        val result = storage.getFromStorage("x")
        assertEquals(20, result)
    }

    // Test para FunctionCallStatement - println
    @Test
    fun testFunctionCallStatementPrintln() {
        val arguments = listOf(StringLiteral("Hello World"))
        val functionCall = FunctionCallStatement("println", arguments)

        // Capturamos la salida de la función println
        val output = captureOutput {
            interpreter.interpret(functionCall)
        }

        assertEquals("Hello World", output.trim())
    }

    // Test para la evaluación de una expresión usando un identificador
    @Test
    fun testIdentifierExpression() {
        storage.addToStorage("y", 50)
        val identifier = IdentifierExpression("y")

        val result = interpreter.interpret(identifier)
        assertEquals(50, result)
    }

    // Test para BinaryExpression con combinación de int y string
    @Test
    fun testBinaryExpressionIntAndStringConcatenation() {
        val left = NumberLiteral(42.0)
        val right = StringLiteral(" is the answer")
        val binaryExpression = BinaryExpression(left, "+", right)
        val result = interpreter.interpret(binaryExpression)
        assertEquals("42 is the answer", result)
    }

    // Test para UnaryExpression con operandos no válidos
    @Test
    fun testUnaryExpressionInvalidOperand() {
        val right = NumberLiteral(33.2)
        val unaryExpression = UnaryExpression("+", right)

        assertThrows(IllegalArgumentException::class.java) {
            interpreter.interpret(unaryExpression)
        }
    }

    // Test para función no definida
    @Test
    fun testUndefinedFunctionCall() {
        val arguments = listOf(NumberLiteral(10.0))
        val functionCall = FunctionCallStatement("undefinedFunction", arguments)

        assertThrows(IllegalArgumentException::class.java) {
            interpreter.interpret(functionCall)
        }
    }

    // Helper para capturar la salida de println
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