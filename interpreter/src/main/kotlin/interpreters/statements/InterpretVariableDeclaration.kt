package com.printscript.interpreter.interpreters.statements

import com.printscript.ast.VariableDeclarationStatement
import com.printscript.interpreter.Interpreter
import com.printscript.interpreter.interfaces.EnvProvider
import com.printscript.interpreter.interfaces.InputProvider
import com.printscript.interpreter.interfaces.InterpreterResult
import com.printscript.interpreter.interfaces.OutPutProvider
import com.printscript.interpreter.results.InterpreterResultInformation
import com.printscript.interpreter.utils.Storage

class InterpretVariableDeclaration(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {

    fun interpret(node: VariableDeclarationStatement, storage: Storage): InterpreterResult {
        val assignment = node.getAssignmentExpression()
        val declarator = node.getDeclarator()
        Interpreter(
            outPutProvider,
            inputProvider,
            envProvider,
        ).interpret(assignment, storage)
        val mutable = storage.getFromStorage(assignment.getIdentifier().getIdentifier())
        when (declarator) {
            "let" -> {
                return InterpreterResultInformation(storage)
            }
            "const" -> {
                val newAssignment = mutable?.unMutable()
                storage.addToStorage(assignment.getIdentifier().getIdentifier(), newAssignment)
                return InterpreterResultInformation(storage)
            }
        }
        return InterpreterResultInformation(storage)
    }
}