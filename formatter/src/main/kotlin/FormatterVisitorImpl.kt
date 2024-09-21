package org.example

import AssignmentStatement
import BinaryExpression
import BooleanLiteral
import EmptyVarDeclarationStatement
import FunctionCallStatement
import IdentifierExpression
import IfStatement
import NumberLiteral
import StringLiteral
import TypeDeclarationExpression
import UnaryExpression
import VariableDeclarationStatement
import Visitor
import org.example.config.FormatterConfig
import org.example.rules.*

class FormatterVisitorImpl(private val config: FormatterConfig, private val builder: StringBuilder): Visitor<Unit> {

    override fun visit(variableDeclarationStatement: VariableDeclarationStatement) {
        applyRules(variableDeclarationStatement.getDeclarator())
        variableDeclarationStatement.getTypeDeclarationExpression().accept(this)
        variableDeclarationStatement.getAssignmentExpression().accept(this)
    }

    override fun visit(ifStatement: IfStatement) {
        TODO("Not yet implemented")
    }

    override fun visit(booleanLiteral: BooleanLiteral) {
        applyRules(booleanLiteral.getValue().toString())
    }

    override fun visit(stringLiteral: StringLiteral) {
        applyRules(stringLiteral.getValue())
    }

    override fun visit(numberLiteral: NumberLiteral) {
        applyRules(numberLiteral.getValue().toString())
    }

    override fun visit(typeDeclarationExpression: TypeDeclarationExpression) {
        applyRules(typeDeclarationExpression.getType())
    }

    override fun visit(identifierExpression: IdentifierExpression) {
        applyRules(identifierExpression.getIdentifier())
    }

    override fun visit(unaryExpression: UnaryExpression) {
        applyRules(unaryExpression.getOperator())
        unaryExpression.getRight().accept(this)
    }

    override fun visit(binaryExpression: BinaryExpression) {
        binaryExpression.getLeft().accept(this)
        applyRules(binaryExpression.getOperator())
        binaryExpression.getRight().accept(this)
    }

    override fun visit(assignmentStatement: AssignmentStatement) {
        assignmentStatement.getIdentifier().accept(this)
        applyRules(assignmentStatement.getEqualOperator())
        assignmentStatement.getValue().accept(this)
    }

    override fun visit(functionCallStatement: FunctionCallStatement) {
        TODO("Not yet implemented")
    }

    override fun visit(emptyVarDeclarationStatement: EmptyVarDeclarationStatement) {
        applyRules(emptyVarDeclarationStatement.getDeclarator())
        emptyVarDeclarationStatement.getIdentifier().accept(this)
        emptyVarDeclarationStatement.getTypeDeclarationExpression().accept(this)
    }

    private fun applyRules(value: String) {
        // aca no sabe q value le llega, tengo q verificar q el value sea el correcto en  cada config
        if (config.spaceBeforeColon) return SpaceBeforeColonRule().apply(value, config, builder)
        if (config.spaceAfterColon) return SpaceAfterColon().apply(value, config, builder)
        if (config.spaceAroundEquals) return SpaceAroundEquals().apply(value, config, builder)
        if (config.lineJumpBeforePrintln > 0) return LineJumpBeforePrintln(config.lineJumpBeforePrintln).apply(value, config, builder)
        if (config.lineJumpAfterSemicolon) return LineJumpAfterSemicolon().apply(value, config, builder)
        if (config.singleSpaceBetweenTokens) return SingleSpaceBetweenTokens().apply(value, config, builder)
        if (config.spaceAroundOperators) return SpaceAroundOperator().apply(value, config, builder)
    }
}