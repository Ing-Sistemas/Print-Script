import org.example.*
import org.example.token.TokenType
import org.junit.jupiter.api.Test

class InterpreterTests {
    val interpreter = Interpreter()

    // let a: number = 4;

    val keyword = Token(TokenType.KEYWORD, "let")
    val type = Token(TokenType.NUMBER_TYPE, "number")
    val typeNode = TypeDeclarationNode(type, 0, 10)
    val tokenIdentifier = Token(TokenType.IDENTIFIER, "a")
    val identifier = IdentifierNode(tokenIdentifier, 0, 10)

    val valueToken = Token(TokenType.LITERAL_NUMBER, "4")
    val valueNode = LiteralNode(valueToken, 0, 10)

    val assignToken = Token(TokenType.ASSIGNMENT, "=")
    val assignment = AssignmentNode(assignToken, identifier, valueNode, 0, 10)

    val firstExample = VariableDeclarationNode(keyword, 0, 10, typeNode, assignment)

    val statement = StatementNode(firstExample, TokenType.SEMICOLON, 0, 10)

    val statementListed = listOf<ASTNode>(statement)
    val myProgram = ProgramNode(0, 10, statementListed)

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
}