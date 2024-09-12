package linters

import configurations.CaseConfiguration
import interfaces.Linter
import org.example.Token
import org.example.TokenType.*

class IdentifierCaseLinter(private val caseConfiguration: CaseConfiguration) : Linter {
    override fun lint(tokens: List<Token>): List<String> {
        val errors = mutableListOf<String>()
        val regex = when (caseConfiguration.value) {
            "camelCase" -> Regex("^[a-z]+(?:[A-Z0-9][a-z0-9]*)*$")
            "snake_case" -> Regex("^[a-z]+(?:_[a-z0-9]+)*$")
            else -> {
                errors.add("Invalid case configuration ${caseConfiguration.value}")
                return errors
            }
        }

        tokens.filter { it.getType() == IDENTIFIER }.forEach { token ->
            if (!regex.matches(token.getValue())) {
                errors.add("Identifier ${token.getValue()} does not match the ${caseConfiguration.value} convention")
            }
        }
        return errors
    }
}