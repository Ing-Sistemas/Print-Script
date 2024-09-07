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
        val result = interpreter.interpret(numberLiteral, storage)
        assertEquals(42.0, result)
    }

    @Test
    fun testStringLiteral() {
        val stringLiteral = StringLiteral("hello")
        val result = interpreter.interpret(stringLiteral, storage)
        assertEquals("hello", result)
    }

    @Test
    fun testBinaryExpressionAddition() {
        val left = NumberLiteral(10.0)
        val right = NumberLiteral(5.0)
        val binaryExpression = BinaryExpression(left, "+", right)
        val result = interpreter.interpret(binaryExpression, storage)
        assertEquals(15.0, result)
    }

    @Test
    fun testBinaryExpressionSubtraction() {
        val left = NumberLiteral(10.0)
        val right = NumberLiteral(5.0)
        val binaryExpression = BinaryExpression(left, "-", right)
        val result = interpreter.interpret(binaryExpression, storage)
        assertEquals(5.0, result)
    }

    @Test
    fun testBinaryExpressionStringConcatenation() {
        val left = StringLiteral("Hello, ")
        val right = StringLiteral("World!")
        val binaryExpression = BinaryExpression(left, "+", right)
        val result = interpreter.interpret(binaryExpression, storage)
        assertEquals("Hello, World!", result)
    }

    @Test
    fun testUnaryExpression() {
        val right = NumberLiteral(5.0)
        val unaryExpression = UnaryExpression("-", right)
        val result = interpreter.interpret(unaryExpression, storage)
        assertEquals(-5.0, result)
    }

    @Test
    fun testVariableDeclarationStatement() {
        val typeDeclaration = TypeDeclarationExpression("number")
        val assignment = AssignmentStatement(IdentifierExpression("x"), NumberLiteral(10.0), "=")
        val variableDeclaration = VariableDeclarationStatement("x", typeDeclaration, assignment)

        interpreter.interpret(variableDeclaration, storage)
        val result = storage.getFromStorage("x")
        println(result)
        assertEquals(10.0, result)
    }

    @Test
    fun testAssignmentStatement() {
        val assignment = AssignmentStatement(IdentifierExpression("x"), NumberLiteral(20.0), "=")
        interpreter.interpret(assignment, storage)
        val result = storage.getFromStorage("x")
        assertEquals(20.0, result)
    }

    @Test
    fun testFunctionCallStatementPrintln() {
        val arguments = listOf(StringLiteral("Hello World"))
        val functionCall = FunctionCallStatement("println", arguments)

        val output = captureOutput {
            interpreter.interpret(functionCall, storage)
        }

        assertEquals("Hello World", output.trim())
    }

    @Test
    fun testIdentifierExpression() {
        storage.addToStorage("y", 50)
        val identifier = IdentifierExpression("y")

        val result = interpreter.interpret(identifier, storage)
        assertEquals(50, result)
    }

    @Test
    fun testBinaryExpressionIntAndStringConcatenation() {
        val left = NumberLiteral(42.0)
        val right = StringLiteral(" is the answer")
        val binaryExpression = BinaryExpression(left, "+", right)
        val result = interpreter.interpret(binaryExpression, storage)
        assertEquals("42.0 is the answer", result)
    }

    @Test
    fun testUnaryExpressionInvalidOperand() {
        val right = NumberLiteral(33.2)
        val unaryExpression = UnaryExpression("+", right)

        assertThrows(IllegalArgumentException::class.java) {
            interpreter.interpret(unaryExpression, storage)
        }
    }

    @Test
    fun testUndefinedFunctionCall() {
        val arguments = listOf(NumberLiteral(10.0))
        val functionCall = FunctionCallStatement("undefinedFunction", arguments)

        assertThrows(IllegalArgumentException::class.java) {
            interpreter.interpret(functionCall, storage)
        }
    }

    @Test
    fun testBinaryExpressionWithMul() {
        val left = NumberLiteral(8.0)
        val right = NumberLiteral(7.0)
        val binaryExpression = BinaryExpression(left, "*", right)
        val result = interpreter.interpret(binaryExpression, storage)
        assertEquals(56.0, result)
    }

    @Test
    fun testNestedBinaryExpressionsAdditionAndMultiplication() {
        val left = NumberLiteral(2.0)
        val right = NumberLiteral(3.0)
        val addition = BinaryExpression(left, "+", right)

        val secondRight = NumberLiteral(4.0)
        val multiplication = BinaryExpression(addition, "*", secondRight)

        val result = interpreter.interpret(multiplication, storage)
        assertEquals(20.0, result)
    }

    @Test
    fun testNestedBinaryExpressionsSubtractionAndDivision() {
        val left = NumberLiteral(10.0)
        val right = NumberLiteral(2.0)
        val division = BinaryExpression(left, "/", right)

        val secondLeft = NumberLiteral(15.0)
        val subtraction = BinaryExpression(secondLeft, "-", division)

        val result = interpreter.interpret(subtraction, storage)
        assertEquals(10.0, result)
    }

    @Test
    fun testComplexBinaryExpressions() {
        val first = BinaryExpression(NumberLiteral(5.0), "+", NumberLiteral(10.0))
        val second = BinaryExpression(NumberLiteral(20.0), "-", NumberLiteral(5.0))
        val third = BinaryExpression(first, "*", second)

        val result = interpreter.interpret(third, storage)
        assertEquals(225.0, result)
    }

    @Test
    fun testBinaryExpressionStringAndDoubleConcatenation() {
        val left = StringLiteral("The result is: ")
        val right = NumberLiteral(42.0)
        val binaryExpression = BinaryExpression(left, "+", right)  

        val result = interpreter.interpret(binaryExpression, storage)
        assertEquals("The result is: 42.0", result)
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