package org.example

interface Visitor<T> {
    //fun visit(astNode : ASTNode): T
    fun visit(identifierNode : IdentifierNode): T
    fun visit(literalNode: LiteralNode): T
    fun visit(typeDeclaration: TypeDeclarationNode): T
    fun visit(variableDeclarationNode: VariableDeclarationNode): T
    fun visit(programNode: ProgramNode): T
    fun visit(statement: StatementNode): T
}