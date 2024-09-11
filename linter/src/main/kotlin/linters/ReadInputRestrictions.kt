package linters

import Token
import configurations.CaseConfiguration
import interfaces.Linter

class ReadInputRestrictions(private val caseConfiguration: CaseConfiguration) : Linter {
    override fun lint(tokens: List<Token>): List<String> {
        val errors = mutableListOf<String>()

        tokens.forEach {token ->
            if (token.getValue() == "readInput") {
                val utilToken = tokens[tokens.indexOf(token) + 2]
                if (utilToken.isLiteralOrIdentifier()) {
                    errors.add("Cannot use readInput with ${utilToken.getValue()} in " +
                        "line: ${utilToken.getPosition().getLine()} column: ${utilToken.getPosition().getColumn()}")
                }
            }
        }
        return errors
    }
    private fun Token.isLiteralOrIdentifier(): Boolean {
        return this.getType().name == "LITERAL" || this.getType().name == "IDENTIFIER"
    }
}