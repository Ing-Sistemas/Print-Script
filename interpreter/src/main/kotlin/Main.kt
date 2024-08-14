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

    println(storage.keys)
    println(storage.values)



}