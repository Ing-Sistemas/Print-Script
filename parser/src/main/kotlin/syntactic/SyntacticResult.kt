package org.example.parser.syntactic

import org.example.ASTNode


/**
 * Sealed interface for pattern matching :)
 *
 * If the syntactic analysis goes well (no errors) the analyser encloses the resulting astNode inside the
 * Success result and sends it to the semantic analyser for its analysis
 *
 * On the other case, it returns a Syntactic error to the user with a message
 * (without the ast becasue it's not able to build one)
 *
 */

sealed interface SyntacticResult

data class Success(private val astNode: ASTNode): SyntacticResult
data class Error(private val message: String): SyntacticResult

