package org.example

import Token
import org.example.token.TokenType

fun main() {
    val interpreter = Interpreter()

    // let a: number = 4;

    val keyword = Token(TokenType.KEYWORD, "let")
    val type = Token(TokenType.NUMBER_TYPE, "number")
    val typeNode = TypeDeclarationNode(type.getValue(), 0, 10)
    val tokenIdentifier = Token(TokenType.IDENTIFIER, "a")
    val identifier = IdentifierNode(tokenIdentifier.getValue(), 0, 10)

    val valueToken = Token(TokenType.LITERAL_NUMBER, "4")
    val valueNode = LiteralNode(valueToken.getValue(), valueToken.getType().toString(),0, 92)

    val assignToken = Token(TokenType.ASSIGNMENT, "=")
    val assignment = AssignmentNode(assignToken.getValue(), identifier, valueNode, 0, 15)

    val firstExample = VariableDeclarationNode(keyword.getValue(), typeNode, assignment, 0,22)

    val statement = StatementNode(firstExample,0, 19)

    val statementListed = listOf<ASTNode>(statement)
    val myProgram = ProgramNode(0, 10, statementListed)
    val storage = interpreter.getStorage()
    interpreter.interpret(myProgram)

    // println("hello world!")

    val printCall = Token(TokenType.CALL, "println" )
    val argument = Token(TokenType.LITERAL_STRING, "hello world!")
    val literalNode2 = LiteralNode(argument.getValue(), argument.getType().toString(), 0,12)
    val listedArguments = listOf<ASTNode>(literalNode2)
    val caller = CallNode(printCall.getValue(), listedArguments, 0, 10)

    val statement2 = StatementNode(caller,0, 52)

    val statementeListed2 = listOf<ASTNode>(statement2)
    val myProgram2 = ProgramNode(0, 55, statementeListed2)

    //let my_var: string = 'ast'; println(my_var);

    val keyword3 = Token(TokenType.KEYWORD, "let")
    val type3 = Token(TokenType.NUMBER_TYPE, "string")
    val typeNode3 = TypeDeclarationNode(type3.getValue(), 0, 10)
    val tokenIdentifier3 = Token(TokenType.IDENTIFIER, "'my_var'")
    val identifier3 = IdentifierNode(tokenIdentifier3.getValue(), 0, 10)

    val valueToken3 = Token(TokenType.LITERAL_NUMBER, "'ast'")
    val valueNode3 = LiteralNode(valueToken3.getValue(), valueToken3.getType().toString(),0, 92)

    val assignToken3 = Token(TokenType.ASSIGNMENT, "=")
    val assignment3 = AssignmentNode(assignToken3.getValue(), identifier3, valueNode3, 0, 15)

    val firstExample3 = VariableDeclarationNode(keyword3.getValue(), typeNode3, assignment3, 0,22)

    val statement3 = StatementNode(firstExample3,0, 19)

    val statementListed3 = listOf<ASTNode>(statement3)
    val myProgram3 = ProgramNode(0, 10, statementListed3)
    interpreter.interpret(myProgram3)


    val printCall2 = Token(TokenType.CALL, "println" )
    val argument2 = Token(TokenType.IDENTIFIER, "'my_var'")
    val identifierNode = IdentifierNode(argument2.getValue(),0,12)
    val newListedArgs = listOf<ASTNode>(identifierNode)
    val caller2 = CallNode(printCall2.getValue(), newListedArgs, 0, 10)

    val statement4 = StatementNode(caller2,0, 52)

    val statementeListed4 = listOf<ASTNode>(statement4)
    val myProgram4 = ProgramNode(0, 55, statementeListed4)

    println(storage.keys)
    println(storage.values)

    println(interpreter.interpret(myProgram2))
    println(interpreter.interpret(myProgram4))
}
