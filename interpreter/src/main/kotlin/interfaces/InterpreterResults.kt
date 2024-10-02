package com.printscript.interpreter.interfaces

import com.printscript.ast.StoredValue
import com.printscript.interpreter.utils.Storage

sealed interface InterpreterResult

interface Success : InterpreterResult {
    val customValue: StoredValue?
    fun getSuccess(): StoredValue?
    fun getOriginalValue(): Any?
    fun getIntValue(): Any
}
interface Failure : InterpreterResult {
    val error: String
}
interface ResultInformation : InterpreterResult {
    val result: Storage
}