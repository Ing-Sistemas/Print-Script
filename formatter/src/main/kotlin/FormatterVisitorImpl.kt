package org.example

import AssignmentStatement
import BinaryExpression
import BooleanLiteral
import EmptyVarDeclarationStatement
import FunctionCallStatement
import IdentifierExpression
import IfStatement
import NumberLiteral
import RuleApplier
import StringLiteral
import TypeDeclarationExpression
import UnaryExpression
import VariableDeclarationStatement
import Visitor
import org.example.config.FormatterConfig

class FormatterVisitorImpl(private val config: FormatterConfig, private val builder: StringBuilder): Visitor<Unit> {

    private val applier = RuleApplier(config, builder)
    override fun visit(variableDeclarationStatement: VariableDeclarationStatement) {
        applier.apply(variableDeclarationStatement.getDeclarator())
        builder.append(" ")
        variableDeclarationStatement.getAssignmentExpression().getIdentifier().accept(this)
        applier.apply(":")
        variableDeclarationStatement.getTypeDeclarationExpression().accept(this)
        applier.apply(variableDeclarationStatement.getAssignmentExpression().getEqualOperator())
        variableDeclarationStatement.getAssignmentExpression().getValue().accept(this)
        builder.append("; \n")
    }

    override fun visit(ifStatement: IfStatement) {
        TODO("Not yet implemented")
    }

    override fun visit(booleanLiteral: BooleanLiteral) {
        applier.apply(booleanLiteral.getValue().toString())
    }

    override fun visit(stringLiteral: StringLiteral) {
        applier.apply(stringLiteral.getValue())
    }

    override fun visit(numberLiteral: NumberLiteral) {
        applier.apply(numberLiteral.getValue().toString())
    }

    override fun visit(typeDeclarationExpression: TypeDeclarationExpression) {
        applier.apply(typeDeclarationExpression.getType())
    }

    override fun visit(identifierExpression: IdentifierExpression) {
        applier.apply(identifierExpression.getIdentifier())
    }

    override fun visit(unaryExpression: UnaryExpression) {
        applier.apply(unaryExpression.getOperator())
        unaryExpression.getRight().accept(this)
    }

    override fun visit(binaryExpression: BinaryExpression) {
        binaryExpression.getLeft().accept(this)
        applier.apply(binaryExpression.getOperator())
        binaryExpression.getRight().accept(this)
    }

    override fun visit(assignmentStatement: AssignmentStatement) {
        assignmentStatement.getIdentifier().accept(this)
        applier.apply(assignmentStatement.getEqualOperator())
        assignmentStatement.getValue().accept(this)
    }

    override fun visit(functionCallStatement: FunctionCallStatement) {
        TODO("Not yet implemented")
    }

    override fun visit(emptyVarDeclarationStatement: EmptyVarDeclarationStatement) {
        applier.apply(emptyVarDeclarationStatement.getDeclarator())
        emptyVarDeclarationStatement.getIdentifier().accept(this)
        emptyVarDeclarationStatement.getTypeDeclarationExpression().accept(this)
    }
}