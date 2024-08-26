import org.example.*
import org.example.token.TokenType
import org.junit.jupiter.api.Test

class InterpreterTests {
    val interpreter = Interpreter()

    // let a: number = 4;

    val aKeyword = Token(TokenType.KEYWORD, "let")
    val aType = Token(TokenType.NUMBER_TYPE, "number")
    val aTypeNode = TypeDeclarationNode(aType.getValue(), 0, 10)
    val aTokenIdentifier = Token(TokenType.IDENTIFIER, "a")
    val aIdentifier = IdentifierNode(aTokenIdentifier.getValue(), 0, 10)

    val aValueToken = Token(TokenType.LITERAL_NUMBER, "4")
    val aValueNode = LiteralNode(aValueToken.getValue(), aValueToken.getType().toString(),0, 92)

    val aAssignToken = Token(TokenType.ASSIGNMENT, "=")
    val aAssignment = AssignmentNode(aAssignToken.getValue(), aIdentifier, aValueNode, 0, 15)

    val firstExample = VariableDeclarationNode(aKeyword.getValue(), aTypeNode, aAssignment, 0,22)

    val statement = StatementNode(firstExample,0, 19)

    val statementListed = listOf<ASTNode>(statement)
    val myProgram = ProgramNode(0, 10, statementListed)
    val storage = interpreter.getStorage()


    // println("hello world!")

    val printCall = Token(TokenType.CALL, "println" )
    val printArgument = Token(TokenType.LITERAL_STRING, "'hello world!'")
    val literalPrintNode = LiteralNode(printArgument.getValue(), printArgument.getType().toString(), 0,12)
    val listedArgumentsForPrint = listOf<ASTNode>(literalPrintNode)
    val printCaller = CallNode(printCall.getValue(), listedArgumentsForPrint, 0, 10)

    val statement2 = StatementNode(printCaller,0, 52)

    val statementListed2 = listOf<ASTNode>(statement2)
    val myProgram2 = ProgramNode(0, 55, statementListed2)

    @Test
    fun testEmptyStorage(){
        val emptyStorage = interpreter.getStorage()
        assert(emptyStorage.isEmpty())
    }

    @Test
    fun testStorageVarDeclaration(){
        val storage = interpreter.getStorage()
        interpreter.interpret(myProgram)
        assert(storage.isNotEmpty())
        assert(storage.containsValue(4))
        assert(storage.containsKey("a"))
    }

//    @Test
//    fun testPrint(){
//        val toPrint = interpreter.interpret(myProgram2)
//        println(toPrint)
//        assert(toPrint == "'hello world!'")
//    }
}