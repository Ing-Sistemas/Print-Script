package interfaces

import StoredValue
import utils.Storage

sealed interface InterpreterResult

interface Success : InterpreterResult {
    val customValue: StoredValue?
    fun getSuccess(): StoredValue?
    fun getOriginalValue(): Any?
}
interface Failure : InterpreterResult {
    val error: String
}
interface ResultInformation : InterpreterResult {
    val result: Storage
}