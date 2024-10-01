package com.printscript.linter.linters

import com.printscript.linter.interfaces.Linter
import com.printscript.token.Token

class PrinterRestrictions : Linter {
    override fun lint(tokens: List<Token>): List<String> {
        val errors = mutableListOf<String>()

        tokens.forEach { token ->
            if (token.getValue() == "println") {
                val utilToken = tokens[tokens.indexOf(token) + 2]
                if (utilToken.isNotLiteralOrIdentifier()) {
                    errors.add(
                        "Cannot use println with ${utilToken.getValue()} in " +
                            "line: ${utilToken.getPosition().getLine()} column: ${utilToken.getPosition().getColumn()}",
                    )
                }
            }
        }
        return errors
    }

    private fun Token.isNotLiteralOrIdentifier(): Boolean {
        return !(this.getType().name == "LITERAL" || this.getType().name == "IDENTIFIER")
    }
}

// si mandatory variable or literal in println es true -> println esta restringido a solo literales e identificadores