package org.example

import Token
import org.example.token.TokenType

fun main() {
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
    interpreter.interpret(myProgram)

    // println("hello world!")

    val printCall = Token(TokenType.CALL, "println" )
    val printArgument = Token(TokenType.LITERAL_STRING, "hello world!")
    val literalPrintNode = LiteralNode(printArgument.getValue(), printArgument.getType().toString(), 0,12)
    val listedArgumentsForPrint = listOf<ASTNode>(literalPrintNode)
    val printCaller = CallNode(printCall.getValue(), listedArgumentsForPrint, 0, 10)

    val statement2 = StatementNode(printCaller,0, 52)

    val statementListed2 = listOf<ASTNode>(statement2)
    val myProgram2 = ProgramNode(0, 55, statementListed2)

    //let my_var: string = 'ast'; println(my_var);

    val myVarKeyword = Token(TokenType.KEYWORD, "let")
    val myVarType = Token(TokenType.NUMBER_TYPE, "string")
    val myVarTypeNode = TypeDeclarationNode(myVarType.getValue(), 0, 10)
    val myVarTokenIdentifier = Token(TokenType.IDENTIFIER, "'my_var'")
    val myVarIdentifier = IdentifierNode(myVarTokenIdentifier.getValue(), 0, 10)

    val myVarValueToken = Token(TokenType.LITERAL_NUMBER, "'ast'")
    val myVarValueNode = LiteralNode(myVarValueToken.getValue(), myVarValueToken.getType().toString(),0, 92)

    val myVarAssignToken = Token(TokenType.ASSIGNMENT, "=")
    val myVarAssignment = AssignmentNode(myVarAssignToken.getValue(), myVarIdentifier, myVarValueNode, 0, 15)

    val myVarExample = VariableDeclarationNode(myVarKeyword.getValue(), myVarTypeNode, myVarAssignment, 0,22)

    val myVarStatement = StatementNode(myVarExample,0, 19)

    val statementListed3 = listOf<ASTNode>(myVarStatement)
    val myProgram3 = ProgramNode(0, 10, statementListed3)
    interpreter.interpret(myProgram3)


    val printCall2 = Token(TokenType.CALL, "println" )
    val argument2 = Token(TokenType.IDENTIFIER, "'my_var'")
    val identifierNode = IdentifierNode(argument2.getValue(),0,12)
    val printCall2ListedArgs = listOf<ASTNode>(identifierNode)
    val caller2 = CallNode(printCall2.getValue(), printCall2ListedArgs, 0, 10)

    val statement4 = StatementNode(caller2,0, 52)

    val statementeListed4 = listOf<ASTNode>(statement4)
    val myProgram4 = ProgramNode(0, 55, statementeListed4)

    println(storage.keys)
    println(storage.values)

    println(interpreter.interpret(myProgram2))
    println(interpreter.interpret(myProgram4))
}
