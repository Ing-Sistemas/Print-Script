package org.example.parser.semantic

import org.example.*

class SemanticNodeVisitor(
    private val typeCheck: TypeCheck,
    private val storageManager: StorageManager,
    private val operationCheck: OperationCheck,
    private val resultFactory: ResultFactory,
) : Visitor<ResultInformation> {

    override fun visit(programNode: ProgramNode): ResultInformation {
        val errors = programNode.getChildren().flatMap { it.accept(this).getErrors() }
        return resultFactory.create(null, null, errors)
    }

    override fun visit(literalNode: LiteralNode): ResultInformation {
        return resultFactory.create(literalNode.getValue(), literalNode.getType())
    }

    override fun visit(typeDeclaration: TypeDeclarationNode): ResultInformation {
        return resultFactory.create(null, typeDeclaration.getValue())
    }

    override fun visit(identifierNode: IdentifierNode): ResultInformation {
        return storageManager.getIdentifierResult(identifierNode)
    }

    override fun visit(variableDeclarationNode: VariableDeclarationNode): ResultInformation {
        return typeCheck.checkVariableDeclaration(variableDeclarationNode, this)
    }

    override fun visit(binaryNode: BinaryNode): ResultInformation {
        return operationCheck.checkBinaryOperation(binaryNode, this)
    }

    override fun visit(assignmentNode: AssignmentNode): ResultInformation {
        return storageManager.handleAssignment(assignmentNode, this)
    }

    override fun visit(statementNode: StatementNode): ResultInformation {
        return statementNode.getStatement().accept(this)
    }

    override fun visit(unaryNode: UnaryNode): ResultInformation {
        return resultFactory.create(null, null)
    }

    override fun visit(callNode: CallNode): ResultInformation {
        return callNode.getArguments().flatMap { it.accept(this).getErrors() }.let {
            resultFactory.create(null, null, it)
        }
    }
}