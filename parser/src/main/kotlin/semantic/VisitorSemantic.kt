package org.example.parser.semantic

import AssignmentStatement
import BinaryExpression
import BooleanLiteral
import EmptyVarDeclarationStatement
import FunctionCallStatement
import IdentifierExpression
import NumberLiteral
import StringLiteral
import TypeDeclarationExpression
import UnaryExpression
import VariableDeclarationStatement
import Visitor

interface VisitorSemantic<T> : Visitor<T>{
    fun visit(variableDeclarationStatement: VariableDeclarationStatement): T
    fun visit(emptyVarDeclarationStatement: EmptyVarDeclarationStatement): T
    fun visit(functionCallStatement: FunctionCallStatement): T
    fun visit(assignmentStatement: AssignmentStatement, isMutable: Boolean): T
    fun visit(binaryExpression: BinaryExpression): T
    fun visit(unaryExpression: UnaryExpression): T
    fun visit(identifierExpression: IdentifierExpression): T
    fun visit(typeDeclarationExpression: TypeDeclarationExpression): T
    fun visit(numberLiteral: NumberLiteral, isMutable: Boolean): T
    fun visit(stringLiteral: StringLiteral, isMutable: Boolean): T
    fun visit(booleanLiteral: BooleanLiteral, isMutable: Boolean): T
}