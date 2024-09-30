package com.printscript.linter.linters

import com.printscript.linter.interfaces.Linter
import com.printscript.token.Token

class ReadInputRestrictions() : Linter {
    override fun lint(tokens: List<Token>): List<String> {
        val errors = mutableListOf<String>()

        tokens.forEach { token ->
            if (token.getValue() == "readInput") {
                val utilToken = tokens[tokens.indexOf(token) + 2]
                if (!utilToken.isIdentifierOrLiteral()) {
                    errors.add(
                        "Cannot use readInput with ${utilToken.getValue()} in " +
                            "line: ${utilToken.getPosition().getLine()} column: ${utilToken.getPosition().getColumn()}",
                    )
                }
            }
        }
        return errors
    }
    private fun Token.isIdentifierOrLiteral(): Boolean {
        return this.getType().name == "LITERAL_NUMBER" || this.getType().name == "LITERAL_STRING" ||
            this.getType().name == "IDENTIFIER"
    }
}
// si mandatory variable or literal in readInput es true -> readInput esta restringido a solo identificadores y literales