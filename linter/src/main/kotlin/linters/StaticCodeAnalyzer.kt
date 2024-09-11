package linters

import Token
import configurations.Configuration
import interfaces.Linter

class StaticCodeAnalyzer(configuration: Configuration) {

    private val linters: List<Linter> = listOf(
        IdentifierCaseLinter(configuration.caseConfiguration),
        if (configuration.restrictPrintln) PrinterRestrictions() else null,
        if (configuration.readInput) ReadInputRestrictions() else null
    ).filterNotNull()

    fun analyze(tokens: List<Token>): List<String> {
        val allErrors = mutableListOf<String>()
        for (linter in linters) {
            allErrors.addAll(linter.lint(tokens))
        }
        return if (allErrors.isEmpty()) {
            emptyList()
        } else {
            allErrors
        }
    }
}