package org.example

import ASTNode
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

class FormatterVisitorImpl(private val config: FormatterConfig, private val builder: StringBuilder): Visitor<ResultFormatter> {

    override fun visit(variableDeclarationStatement: VariableDeclarationStatement): ResultFormatter {
        applyRules(variableDeclarationStatement)
        variableDeclarationStatement.getTypeDeclarationExpression().accept(this)
        variableDeclarationStatement.getAssignmentExpression().accept(this)
        return ResultFormatter() // todo() ver q conviene q devuelva
    }

    override fun visit(ifStatement: IfStatement): ResultFormatter {
        TODO("Not yet implemented")
    }

    override fun visit(booleanLiteral: BooleanLiteral): ResultFormatter {
        applyRules(booleanLiteral)
        return ResultFormatter()
    }

    override fun visit(stringLiteral: StringLiteral): ResultFormatter {
        applyRules(stringLiteral)
        return ResultFormatter()
    }

    override fun visit(numberLiteral: NumberLiteral): ResultFormatter {
        applyRules(numberLiteral)
        return ResultFormatter()
    }

    override fun visit(typeDeclarationExpression: TypeDeclarationExpression): ResultFormatter {
        applyRules(typeDeclarationExpression)
        return ResultFormatter()
    }

    override fun visit(identifierExpression: IdentifierExpression): ResultFormatter {
        applyRules(identifierExpression)
        return ResultFormatter()
    }

    override fun visit(unaryExpression: UnaryExpression): ResultFormatter {
        applyRules(unaryExpression)
        unaryExpression.getRight().accept(this)
        return ResultFormatter()
    }

    override fun visit(binaryExpression: BinaryExpression): ResultFormatter {
        binaryExpression.getLeft().accept(this)
        applyRules(binaryExpression)
        binaryExpression.getRight().accept(this)
        return ResultFormatter()
    }

    override fun visit(assignmentStatement: AssignmentStatement): ResultFormatter {
        assignmentStatement.getIdentifier().accept(this)
        applyRules(assignmentStatement)
        assignmentStatement.getValue().accept(this)
        return ResultFormatter()
    }

    override fun visit(functionCallStatement: FunctionCallStatement): ResultFormatter {
        TODO("Not yet implemented")
    }

    override fun visit(emptyVarDeclarationStatement: EmptyVarDeclarationStatement): ResultFormatter {
        applyRules(emptyVarDeclarationStatement.getDeclarator())
        emptyVarDeclarationStatement.getIdentifier().accept(this)
        emptyVarDeclarationStatement.getTypeDeclarationExpression().accept(this)
        return ResultFormatter()
    }

    private fun applyRules(value: String) {
        // aca no sabe q value le llega, tengo q verificar q el value sea el correcto en  cada config
        if (config.spaceBeforeColon) SpaceBeforeColonRule().format(value, config, builder)
        if (config.spaceAfterColon) SpaceAfterColon().format(value, config, builder)
        if (config.spaceAroundEquals) SpaceAroundEquals().format(value, config, builder)
        if (config.lineJumpBeforePrintln > 0) LineJumpBeforePrintln(config.lineJumpBeforePrintln).format(value, config, builder)
        if (config.lineJumpAfterSemicolon) LineJumpAfterSemicolon().format(value, config, builder)
        if (config.singleSpaceBetweenTokens) SingleSpaceBetweenTokens().format(value, config, builder)
        if (config.spaceAroundOperators) SpaceAroundOperator().format(value, config, builder)
    }
}