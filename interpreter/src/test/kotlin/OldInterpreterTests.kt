import org.example.*
import org.example.token.TokenType
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class OldInterpreterTests {
    val oldInterpreter = OldInterpreter()

    // let a: number = 4;

    val aKeyword = Token(TokenType.KEYWORD, "let")
    val aType = Token(TokenType.NUMBER_TYPE, "number")
    val aTypeNode = TypeDeclarationNode(aType.getValue(), 0, 10)
    val aTokenIdentifier = Token(TokenType.IDENTIFIER, "a")
    val aIdentifier = IdentifierNode(aTokenIdentifier.getValue(), 0, 10)

    val aValueToken = Token(TokenType.LITERAL_NUMBER, "4")
    val aValueNode = LiteralNode(aValueToken.getValue(), aValueToken.getType().toString(), 0, 92)

    val aAssignToken = Token(TokenType.ASSIGNMENT, "=")
    val aAssignment = AssignmentNode(aAssignToken.getValue(), aIdentifier, aValueNode, 0, 15)

    val firstExample = VariableDeclarationNode(aKeyword.getValue(), aTypeNode, aAssignment, 0, 22)

    val statement = StatementNode(firstExample, 0, 19)

    val statementListed = listOf<ASTNode>(statement)
    val myProgram = ProgramNode(0, 10, statementListed)
    val storage = oldInterpreter.getStorage()

    // println("hello world!")

    val printCall = Token(TokenType.CALL, "println")
    val printArgument = Token(TokenType.LITERAL_STRING, "'hello world!'")
    val literalPrintNode = LiteralNode(printArgument.getValue(), printArgument.getType().toString(), 0, 12)
    val listedArgumentsForPrint = listOf<ASTNode>(literalPrintNode)
    val printCaller = CallNode(printCall.getValue(), listedArgumentsForPrint, 0, 10)

    val statement2 = StatementNode(printCaller, 0, 52)

    val statementListed2 = listOf<ASTNode>(statement2)
    val myProgram2 = ProgramNode(0, 55, statementListed2)

    // let b: number = 2 + 3;

    val bKeyword = Token(TokenType.KEYWORD, "let")
    val bType = Token(TokenType.NUMBER_TYPE, "number")
    val bTypeNode = TypeDeclarationNode(bType.getValue(), 0, 10)
    val bTokenIdentifier = Token(TokenType.IDENTIFIER, "b")
    val bIdentifier = IdentifierNode(bTokenIdentifier.getValue(), 0, 10)

    val leftValueToken = Token(TokenType.LITERAL_NUMBER, "2")
    val leftValueNode = LiteralNode(leftValueToken.getValue(), leftValueToken.getType().toString(), 0, 12)

    val rightValueToken = Token(TokenType.LITERAL_NUMBER, "3")
    val rightValueNode = LiteralNode(rightValueToken.getValue(), rightValueToken.getType().toString(), 0, 14)

    val additionToken = Token(TokenType.PLUS_OPERATOR, "+")
    val binaryNode = BinaryNode(additionToken.getValue(), leftValueNode, rightValueNode, 0, 16)

    val bAssignToken = Token(TokenType.ASSIGNMENT, "=")
    val bAssignment = AssignmentNode(bAssignToken.getValue(), bIdentifier, binaryNode, 0, 18)

    val secondExample = VariableDeclarationNode(bKeyword.getValue(), bTypeNode, bAssignment, 0, 20)

    val statement3 = StatementNode(secondExample, 0, 22)

    val statementListed3 = listOf<ASTNode>(statement3)
    val myProgram3 = ProgramNode(0, 25, statementListed3)

    @Test
    fun testEmptyStorage() {
        val emptyStorage = oldInterpreter.getStorage()
        assert(emptyStorage.isEmpty())
    }

    @Test
    fun testStorageVarDeclaration() {
        oldInterpreter.interpret(myProgram)
        assert(storage.isNotEmpty())
        assert(storage.containsValue(4))
        assert(storage.containsKey("a"))
    }

    @Test
    fun testPrint() {
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        System.setOut(printStream)

        oldInterpreter.interpret(myProgram2)

        val result = outputStream.toString().trim()
        val toEqual = "'hello world!'"

        assert(result == toEqual) {
            "Expected $toEqual but got $result"
        }
        System.setOut(System.out)
    }

    @Test
    fun testBinaryNodeOperation() {
        oldInterpreter.interpret(myProgram3)
        val storage = oldInterpreter.getStorage()
        assert(storage.isNotEmpty())
        assert(storage.containsValue(5))
        assert(storage.containsKey("b"))
    }

    @Test
    fun testStringVariableDeclaration() {
        // let c: string = 'hello';
        val cKeyword = Token(TokenType.KEYWORD, "let")
        val cType = Token(TokenType.STRING_TYPE, "string")
        val cTypeNode = TypeDeclarationNode(cType.getValue(), 0, 10)
        val cTokenIdentifier = Token(TokenType.IDENTIFIER, "c")
        val cIdentifier = IdentifierNode(cTokenIdentifier.getValue(), 0, 10)

        val cValueToken = Token(TokenType.LITERAL_STRING, "'hello'")
        val cValueNode = LiteralNode(cValueToken.getValue(), cValueToken.getType().toString(), 0, 12)

        val cAssignToken = Token(TokenType.ASSIGNMENT, "=")
        val cAssignment = AssignmentNode(cAssignToken.getValue(), cIdentifier, cValueNode, 0, 15)

        val thirdExample = VariableDeclarationNode(cKeyword.getValue(), cTypeNode, cAssignment, 0, 22)
        val statement = StatementNode(thirdExample, 0, 19)
        val statementListed = listOf<ASTNode>(statement)
        val myProgram = ProgramNode(0, 10, statementListed)

        oldInterpreter.interpret(myProgram)
        val storage = oldInterpreter.getStorage()
        assert(storage.isNotEmpty())
        assert(storage.containsValue("'hello'"))
        assert(storage.containsKey("c"))
    }

    @Test
    fun testStringConcatenation() {
        // let d: string = 'hello' + 'world';
        val dKeyword = Token(TokenType.KEYWORD, "let")
        val dType = Token(TokenType.STRING_TYPE, "string")
        val dTypeNode = TypeDeclarationNode(dType.getValue(), 0, 10)
        val dTokenIdentifier = Token(TokenType.IDENTIFIER, "d")
        val dIdentifier = IdentifierNode(dTokenIdentifier.getValue(), 0, 10)

        val leftStringToken = Token(TokenType.LITERAL_STRING, "'hello'")
        val leftStringNode = LiteralNode(leftStringToken.getValue(), leftStringToken.getType().toString(), 0, 12)

        val rightStringToken = Token(TokenType.LITERAL_STRING, "'world'")
        val rightStringNode = LiteralNode(rightStringToken.getValue(), rightStringToken.getType().toString(), 0, 14)

        val additionToken = Token(TokenType.PLUS_OPERATOR, "+")
        val binaryNode = BinaryNode(additionToken.getValue(), leftStringNode, rightStringNode, 0, 16)

        val dAssignToken = Token(TokenType.ASSIGNMENT, "=")
        val dAssignment = AssignmentNode(dAssignToken.getValue(), dIdentifier, binaryNode, 0, 18)

        val fourthExample = VariableDeclarationNode(dKeyword.getValue(), dTypeNode, dAssignment, 0, 20)
        val statement = StatementNode(fourthExample, 0, 22)
        val statementListed = listOf<ASTNode>(statement)
        val myProgram = ProgramNode(0, 25, statementListed)

        oldInterpreter.interpret(myProgram)
        val storage = oldInterpreter.getStorage()
        assert(storage.isNotEmpty())
        assert(storage.containsValue("'hello''world'"))
        assert(storage.containsKey("d"))
    }

    @Test
    fun testMultiplication() {
        // let e: number = 5 * 4;
        val bKeyword = Token(TokenType.KEYWORD, "let")
        val bType = Token(TokenType.NUMBER_TYPE, "number")
        val bTypeNode = TypeDeclarationNode(bType.getValue(), 0, 10)
        val bTokenIdentifier = Token(TokenType.IDENTIFIER, "b")
        val bIdentifier = IdentifierNode(bTokenIdentifier.getValue(), 0, 10)

        val leftValueToken = Token(TokenType.LITERAL_NUMBER, "2")
        val leftValueNode = LiteralNode(leftValueToken.getValue(), leftValueToken.getType().toString(), 0, 12)

        val rightValueToken = Token(TokenType.LITERAL_NUMBER, "3")
        val rightValueNode = LiteralNode(rightValueToken.getValue(), rightValueToken.getType().toString(), 0, 14)

        val additionToken = Token(TokenType.MULTIPLY_OPERATOR, "*")
        val binaryNode = BinaryNode(additionToken.getValue(), leftValueNode, rightValueNode, 0, 16)

        val bAssignToken = Token(TokenType.ASSIGNMENT, "=")
        val bAssignment = AssignmentNode(bAssignToken.getValue(), bIdentifier, binaryNode, 0, 18)

        val secondExample = VariableDeclarationNode(bKeyword.getValue(), bTypeNode, bAssignment, 0, 20)

        val statement3 = StatementNode(secondExample, 0, 22)

        val statementListed3 = listOf<ASTNode>(statement3)
        val myProgram3 = ProgramNode(0, 25, statementListed3)

        oldInterpreter.interpret(myProgram3)
        val storage = oldInterpreter.getStorage()
        assert(storage.isNotEmpty())
        assert(storage.containsValue(6))
        assert(storage.containsKey("b"))
    }

    @Test
    fun testSubtraction() {
        // let e: number = 10 - 3;
        val bKeyword = Token(TokenType.KEYWORD, "let")
        val bType = Token(TokenType.NUMBER_TYPE, "number")
        val bTypeNode = TypeDeclarationNode(bType.getValue(), 0, 10)
        val bTokenIdentifier = Token(TokenType.IDENTIFIER, "b")
        val bIdentifier = IdentifierNode(bTokenIdentifier.getValue(), 0, 10)

        val leftValueToken = Token(TokenType.LITERAL_NUMBER, "10")
        val leftValueNode = LiteralNode(leftValueToken.getValue(), leftValueToken.getType().toString(), 0, 12)

        val rightValueToken = Token(TokenType.LITERAL_NUMBER, "3")
        val rightValueNode = LiteralNode(rightValueToken.getValue(), rightValueToken.getType().toString(), 0, 14)

        val additionToken = Token(TokenType.MINUS_OPERATOR, "-")
        val binaryNode = BinaryNode(additionToken.getValue(), leftValueNode, rightValueNode, 0, 16)

        val bAssignToken = Token(TokenType.ASSIGNMENT, "=")
        val bAssignment = AssignmentNode(bAssignToken.getValue(), bIdentifier, binaryNode, 0, 18)

        val secondExample = VariableDeclarationNode(bKeyword.getValue(), bTypeNode, bAssignment, 0, 20)

        val statement3 = StatementNode(secondExample, 0, 22)

        val statementListed3 = listOf<ASTNode>(statement3)
        val myProgram3 = ProgramNode(0, 25, statementListed3)

        oldInterpreter.interpret(myProgram3)
        val storage = oldInterpreter.getStorage()
        assert(storage.isNotEmpty())
        assert(storage.containsValue(7))
        assert(storage.containsKey("b"))
    }

    @Test
    fun testDivition() {
        // let e: number = 10 / 2;
        val bKeyword = Token(TokenType.KEYWORD, "let")
        val bType = Token(TokenType.NUMBER_TYPE, "number")
        val bTypeNode = TypeDeclarationNode(bType.getValue(), 0, 10)
        val bTokenIdentifier = Token(TokenType.IDENTIFIER, "b")
        val bIdentifier = IdentifierNode(bTokenIdentifier.getValue(), 0, 10)

        val leftValueToken = Token(TokenType.LITERAL_NUMBER, "10")
        val leftValueNode = LiteralNode(leftValueToken.getValue(), leftValueToken.getType().toString(), 0, 12)

        val rightValueToken = Token(TokenType.LITERAL_NUMBER, "2")
        val rightValueNode = LiteralNode(rightValueToken.getValue(), rightValueToken.getType().toString(), 0, 14)

        val additionToken = Token(TokenType.DIVIDE_OPERATOR, "/")
        val binaryNode = BinaryNode(additionToken.getValue(), leftValueNode, rightValueNode, 0, 16)

        val bAssignToken = Token(TokenType.ASSIGNMENT, "=")
        val bAssignment = AssignmentNode(bAssignToken.getValue(), bIdentifier, binaryNode, 0, 18)

        val secondExample = VariableDeclarationNode(bKeyword.getValue(), bTypeNode, bAssignment, 0, 20)

        val statement3 = StatementNode(secondExample, 0, 22)

        val statementListed3 = listOf<ASTNode>(statement3)
        val myProgram3 = ProgramNode(0, 25, statementListed3)

        oldInterpreter.interpret(myProgram3)
        val storage = oldInterpreter.getStorage()
        assert(storage.isNotEmpty())
        assert(storage.containsValue(5))
        assert(storage.containsKey("b"))
    }

    @Test
    fun testStringAndNumber() {
        // let d: string = 'hello' + 5;
        val dKeyword = Token(TokenType.KEYWORD, "let")
        val dType = Token(TokenType.STRING_TYPE, "string")
        val dTypeNode = TypeDeclarationNode(dType.getValue(), 0, 10)
        val dTokenIdentifier = Token(TokenType.IDENTIFIER, "d")
        val dIdentifier = IdentifierNode(dTokenIdentifier.getValue(), 0, 10)

        val leftStringToken = Token(TokenType.LITERAL_STRING, "'hello'")
        val leftStringNode = LiteralNode(leftStringToken.getValue(), leftStringToken.getType().toString(), 0, 12)

        val rightStringToken = Token(TokenType.LITERAL_NUMBER, "5")
        val rightStringNode = LiteralNode(rightStringToken.getValue(), rightStringToken.getType().toString(), 0, 14)

        val additionToken = Token(TokenType.PLUS_OPERATOR, "+")
        val binaryNode = BinaryNode(additionToken.getValue(), leftStringNode, rightStringNode, 0, 16)

        val dAssignToken = Token(TokenType.ASSIGNMENT, "=")
        val dAssignment = AssignmentNode(dAssignToken.getValue(), dIdentifier, binaryNode, 0, 18)

        val fourthExample = VariableDeclarationNode(dKeyword.getValue(), dTypeNode, dAssignment, 0, 20)
        val statement = StatementNode(fourthExample, 0, 22)
        val statementListed = listOf<ASTNode>(statement)
        val myProgram = ProgramNode(0, 25, statementListed)

        oldInterpreter.interpret(myProgram)
        val storage = oldInterpreter.getStorage()
        assert(storage.isNotEmpty())
        assert(storage.containsValue("'hello'5"))
        assert(storage.containsKey("d"))
    }

    @Test
    fun testEvaluateNodeWithIdentifierNode() {
        val aTokenIdentifier = Token(TokenType.IDENTIFIER, "a")
        val aIdentifierNode = IdentifierNode(aTokenIdentifier.getValue(), 0, 10)

        oldInterpreter.getStorage()["a"] = 42

        val result = oldInterpreter.interpret(aIdentifierNode)

        assert(result == 42) {
            "Expected 42 but got $result"
        }
    }

    @Test
    fun testVisitAssignment() {
        // let a: number = 5;

        val aTokenIdentifier = Token(TokenType.IDENTIFIER, "a")
        val aIdentifierNode = IdentifierNode(aTokenIdentifier.getValue(), 0, 10)

        val aValueToken = Token(TokenType.LITERAL_NUMBER, "5")
        val aValueNode = LiteralNode(aValueToken.getValue(), aValueToken.getType().toString(), 0, 12)

        val assignToken = Token(TokenType.ASSIGNMENT, "=")
        val assignmentNode = AssignmentNode(assignToken.getValue(), aIdentifierNode, aValueNode, 0, 15)

        oldInterpreter.interpret(assignmentNode)
        oldInterpreter.interpret(aValueNode)
        val storage = oldInterpreter.getStorage()
        assert(storage.containsKey("a"))
        assert(storage["a"] == 5) {
            "Expected 5 but got ${storage["a"]}"
        }
    }
}