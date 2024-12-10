package com.printscript.formatter

import com.printscript.ast.*
import com.printscript.formatter.config.FormatterConfig

class FormatterVisitorImpl(private val config: FormatterConfig, private val builder: StringBuilder) : Visitor<Unit> {

    private val applier = RuleApplier(config, builder)

    override fun visit(variableDeclarationStatement: VariableDeclarationStatement) {
        applier.apply(variableDeclarationStatement.getDeclarator())
        builder.append(" ")
        variableDeclarationStatement.getAssignmentExpression().getIdentifier().accept(this)
        applier.apply(":")
        variableDeclarationStatement.getTypeDeclarationExpression().accept(this)
        applier.apply(variableDeclarationStatement.getAssignmentExpression().getEqualOperator())
        variableDeclarationStatement.getAssignmentExpression().getValue().accept(this)
        builder.append(";\n")
    }

    override fun visit(ifStatement: IfStatement) {
        builder.append("if (")
        ifStatement.getCondition().accept(this)
        builder.append(") {\n")
        visitBlock(ifStatement.getThenStatement(), 4)
        builder.append("}")
        if (ifStatement.getElseStatement() != null) {
            builder.append(" else {\n")
            visitBlock(ifStatement.getElseStatement()!!, 4)
            builder.append("}")
        }
    }

    override fun visit(readInputNode: ReadInputNode) {
        return
    }

    override fun visit(readEnvNode: ReadEnvNode) {
        return
    }

    override fun visit(booleanLiteral: BooleanLiteral) {
        applier.apply(booleanLiteral.getValue().toString())
    }

    override fun visit(stringLiteral: StringLiteral) {
        builder.append('"')
        applier.apply(stringLiteral.getValue())
        builder.append('"')
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
        if (config.lineJumpBeforePrintln != null) {
            repeat(config.lineJumpBeforePrintln) {
                builder.append("\n")
            }
        }

        builder.append(functionCallStatement.getFunctionName())
        builder.append("(")
        functionCallStatement.getArguments().forEachIndexed { index, argument ->
            argument.accept(this)
            if (index < functionCallStatement.getArguments().size - 1) {
                builder.append(", ")
            }
        }
        builder.append(");")
    }

    override fun visit(emptyVarDeclarationStatement: EmptyVarDeclarationStatement) {
        applier.apply(emptyVarDeclarationStatement.getDeclarator())
        emptyVarDeclarationStatement.getIdentifier().accept(this)
        emptyVarDeclarationStatement.getTypeDeclarationExpression().accept(this)
    }

    private fun visitBlock(block: List<ASTNode>, indent: Int) {
        block.forEach { node ->
            builder.append(" ".repeat(indent))
            node.accept(this)
            builder.append("\n")
        }
    }
}