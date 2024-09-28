package com.printscript.ast

interface Visitor<T> {
    fun visit(variableDeclarationStatement: VariableDeclarationStatement): T
    fun visit(emptyVarDeclarationStatement: EmptyVarDeclarationStatement): T
    fun visit(functionCallStatement: FunctionCallStatement): T
    fun visit(assignmentStatement: AssignmentStatement): T
    fun visit(binaryExpression: BinaryExpression): T
    fun visit(unaryExpression: UnaryExpression): T
    fun visit(identifierExpression: IdentifierExpression): T
    fun visit(typeDeclarationExpression: TypeDeclarationExpression): T
    fun visit(numberLiteral: NumberLiteral): T
    fun visit(stringLiteral: StringLiteral): T
    fun visit(booleanLiteral: BooleanLiteral): T
    fun visit(ifStatement: IfStatement): T
    fun visit(readInputNode: ReadInputNode): T
    fun visit(readEnvNode: ReadEnvNode): T
}