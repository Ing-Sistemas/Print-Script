package org.example

import Token
import org.example.token.TokenType

fun main() {
    val interpreter = Interpreter()

    // let a: number = 4;

    val keyword = Token(TokenType.KEYWORD, "let")
    val type = Token(TokenType.NUMBER_TYPE, "number")
    val typeNode = TypeDeclarationNode(type, 0, 10)
    val tokenIdentifier = Token(TokenType.IDENTIFIER, "a")
    val identifier = IdentifierNode(tokenIdentifier, 0, 10)

    val valueToken = Token(TokenType.LITERAL_NUMBER, "4")
    val valueNode = LiteralNode(valueToken, 0, 22)

    val assignToken = Token(TokenType.ASSIGNMENT, "=")
    val assignment = AssignmentNode(assignToken, identifier, valueNode, 0, 15)

    val firstExample = VariableDeclarationNode(keyword, 0, 20, typeNode, assignment)

    val statement = StatementNode(firstExample, TokenType.SEMICOLON, 0, 19)

    val statementListed = listOf<ASTNode>(statement)
    val myProgram = ProgramNode(0, 10, statementListed)
    val storage = interpreter.getStorage()
    interpreter.interpret(myProgram)

    // println("hello world!")

    val printCall = Token(TokenType.CALL, "println" )
    val argument = Token(TokenType.LITERAL_STRING, "kayu se la come")
    val literalNode2 = LiteralNode(argument, 0, 12)
    val listedArguments = listOf<ASTNode>(literalNode2)
    val caller = CallNode(printCall, listedArguments, 0, 10)

    val statement2 = StatementNode(caller, TokenType.CLOSING_PARENS, 0, 22)

    val statementeListed2 = listOf<ASTNode>(statement2)
    val myProgram2 = ProgramNode(0, 55, statementeListed2)

    println(storage.keys)
    println(storage.values)

    println(interpreter.interpret(myProgram2))



}