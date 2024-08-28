package linters

import Token
import configurations.Configuration
import interfaces.Linter

class Linter(configuration: Configuration) {

    private val linters: List<Linter> = listOf(
        IdentifierCaseLinter(configuration.caseConfiguration),
        if (configuration.restrictPrintln) PrinterRestrictions() else null,
    ).filterNotNull()

    fun lint(tokens: List<Token>): List<String> {
        val allErrors = mutableListOf<String>()
        for (linter in linters) {
            allErrors.add(linter.lint(tokens).toString())
        }
        return allErrors
    }
}