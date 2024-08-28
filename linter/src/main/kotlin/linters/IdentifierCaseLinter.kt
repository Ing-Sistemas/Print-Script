package linters

import Token
import configurations.CaseConfiguration
import interfaces.Linter
import org.example.token.TokenType

class IdentifierCaseLinter(private val caseConfiguration: CaseConfiguration) : Linter {
    override fun lint(tokens: List<Token>): List<String> {
        val errors = mutableListOf<String>()
        val regex = when (caseConfiguration.value) {
            "Camel Case" -> Regex("^[a-z]+(?:[A-Z][a-z]+)*$")
            "Snake Case" -> Regex("^[a-z]+(?:_[a-z]+)*$")
            else -> {
                errors.add("Invalid case configuration ${caseConfiguration.value}")
                return errors
            }
        }

        tokens.filter { it.getType() == TokenType.IDENTIFIER }.forEach { token ->
            if (!regex.matches(token.getValue())) {
                errors.add("Identifier ${token.getValue()} does not match the ${caseConfiguration.value} convention")
            }
        }
        return errors
    }
}