package com.printscript.linter.linters

import com.printscript.linter.interfaces.Linter
import com.printscript.token.Token

class PrinterRestrictions : Linter {
    override fun lint(tokens: List<Token>): List<String> {
        val errors = mutableListOf<String>()

        tokens.forEachIndexed { index, token ->
            if (token.getValue() == "println") {
                val openingParenIndex = tokens.indexOfFirst { it.getType().name == "OPENING_PARENS" && tokens.indexOf(it) > index }
                val closingParenIndex = tokens.indexOfFirst { it.getType().name == "CLOSING_PARENS" && tokens.indexOf(it) > openingParenIndex }
                if (openingParenIndex == -1 || closingParenIndex == -1) {
                    errors.add("Syntax error: missing parentheses for println at line: ${token.getPosition().getLine()}")
                } else {
                    for (i in openingParenIndex + 1 until closingParenIndex) {
                        val utilToken = tokens[i]
                        if (utilToken.isNotLiteralOrIdentifier()) {
                            errors.add(
                                "Cannot use println with ${utilToken.getValue()}, type: ${utilToken.getType()} in " +
                                    "line: ${utilToken.getPosition().getLine()} column: ${utilToken.getPosition().getColumn()}",
                            )
                        }
                    }
                }
            }
        }
        return errors
    }

    private fun Token.isNotLiteralOrIdentifier(): Boolean {
        return !(this.getType().name == "LITERAL_NUMBER" || this.getType().name == "IDENTIFIER" || this.getType().name == "LITERAL_STRING")
    }
}