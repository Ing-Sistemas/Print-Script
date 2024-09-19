package linters

import Token
import configurations.Configuration
import interfaces.Linter

class StaticCodeAnalyzer(configuration: Configuration, private val version: String) {

    private val linters0: List<Linter> = listOfNotNull(
        IdentifierCaseLinter(configuration.caseConfiguration),
        if (configuration.restrictPrintln) PrinterRestrictions() else null,
    )

    private val linters1: List<Linter> = listOfNotNull(
        IdentifierCaseLinter(configuration.caseConfiguration),
        if (configuration.restrictPrintln) PrinterRestrictions() else null,
        if (configuration.readInput) ReadInputRestrictions() else null,
    )

    fun analyze(tokens: List<Token>): List<String> {
        val allErrors = mutableListOf<String>()
        return when (version) {
            "1.1" -> {
                for (linter in linters1) {
                    allErrors.addAll(linter.lint(tokens))
                }
                if (allErrors.isEmpty()) {
                    emptyList()
                } else {
                    allErrors
                }
            }

            "1.0" -> {
                for (linter in linters0) {
                    allErrors.addAll(linter.lint(tokens))
                }
                if (allErrors.isEmpty()) {
                    emptyList()
                } else {
                    allErrors
                }
            }

            else -> {
                throw IllegalArgumentException("Unsupported version: $version")
            }
        }
    }
}