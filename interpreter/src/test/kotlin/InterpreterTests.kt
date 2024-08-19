import org.example.*
import org.example.token.TokenType
import org.junit.jupiter.api.Test

class InterpreterTests {
    val interpreter = Interpreter()

    // let a: number = 4;

//    val keyword = Token(TokenType.KEYWORD, "let")
//    val type = Token(TokenType.NUMBER_TYPE, "number")
//    val typeNode = TypeDeclarationNode(type, 0, 10)
//    val tokenIdentifier = Token(TokenType.IDENTIFIER, "a")
//    val identifier = IdentifierNode(tokenIdentifier, 0, 10)
//
//    val valueToken = Token(TokenType.LITERAL_NUMBER, "4")
//    val valueNode = LiteralNode(valueToken, 0, 10)
//
//    val assignToken = Token(TokenType.ASSIGNMENT, "=")
//    val assignment = AssignmentNode(assignToken, identifier, valueNode, 0, 10)
//
//    val firstExample = VariableDeclarationNode(keyword, 0, 10, typeNode, assignment)
//
//    val statement = StatementNode(firstExample, TokenType.SEMICOLON, 0, 10)
//
//    val statementListed = listOf<ASTNode>(statement)
//    val myProgram = ProgramNode(0, 10, statementListed)
//
//    // println("hello world!")
//
//    val printCall = Token(TokenType.CALL, "println" )
//    val argument = Token(TokenType.LITERAL_STRING, "hello world!")
//    val literalNode2 = LiteralNode(argument, 0, 12)
//    val listedArguments = listOf<ASTNode>(literalNode2)
//    val caller = CallNode(printCall, listedArguments, 0, 10)
//
//    val statement2 = StatementNode(caller, TokenType.CLOSING_PARENS, 0, 22)
//
//    val statementeListed2 = listOf<ASTNode>(statement2)
//    val myProgram2 = ProgramNode(0, 55, statementeListed2)
//

    @Test
    fun testEmptyStorage(){
        val emptyStorage = interpreter.getStorage()
        assert(emptyStorage.isEmpty())
    }

    @Test
    fun testStorageVarDeclaration(){
        val storage = interpreter.getStorage()
        //interpreter.interpret(myProgram)
        assert(storage.isNotEmpty())
        assert(storage.containsValue(4))
        assert(storage.containsKey("a"))
    }

    @Test
    fun testPrint(){
        //  val interpreted = interpreter.interpret(myProgram2)
        //println("[$interpreted]88")
        //  println(interpreter.interpret(myProgram2))
        //assert(interpreter.interpret(myProgram2) == "hello world!")
    }
}