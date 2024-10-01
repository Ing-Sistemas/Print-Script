package com.printscript.linter.linters

import com.printscript.linter.interfaces.Linter
import com.printscript.token.Token
import com.printscript.token.TokenType
import configurations.IdentifierFormat

class IdentifierCaseLinter(private val identifierFormat: IdentifierFormat) : Linter {
    override fun lint(tokens: List<Token>): List<String> {
        val errors = mutableListOf<String>()
        val regex = when (identifierFormat.value) {
            "camel case" -> Regex("^[a-z]+(?:[A-Z0-9][a-z0-9]*)*$")
            "snake case" -> Regex("^[a-z]+(?:_[a-z0-9]+)*$")
            else -> {
                errors.add("Invalid case configuration ${identifierFormat.value}")
                return errors
            }
        }

        tokens.filter { it.getType() == TokenType.IDENTIFIER }.forEach { token ->
            if (!regex.matches(token.getValue())) {
                errors.add(
                    "Identifier ${token.getValue()} does not match the ${identifierFormat.value} convention in " +
                        "line: ${token.getPosition().getLine()} column: ${token.getPosition().getColumn()}",
                )
            }
        }
        return errors
    }
}