package com.printscript.linter.linters

import com.printscript.linter.configurations.Configuration
import com.printscript.linter.interfaces.Linter
import com.printscript.token.Token

class StaticCodeAnalyzer(configuration: Configuration, private val version: String) {

    private val linters0: List<Linter>? = listOfNotNull(
        IdentifierCaseLinter(configuration.identifier_format),
        if (configuration.`mandatory-variable-or-literal-in-println`) PrinterRestrictions() else null,
    ).takeIf { it.isNotEmpty() }

    private val linters1: List<Linter>? = listOfNotNull(
        IdentifierCaseLinter(configuration.identifier_format),
        if (configuration.`mandatory-variable-or-literal-in-println`) PrinterRestrictions() else null,
        if (configuration.`mandatory-variable-or-literal-in-readInput`) ReadInputRestrictions() else null,
    ).takeIf { it.isNotEmpty() }

    fun analyze(tokens: List<Token>): List<String> {
        val allErrors = mutableListOf<String>()
        when (version) {
            "1.1" -> {
                linters1?.forEach { linter ->
                    allErrors.addAll(linter.lint(tokens))
                }
            }
            "1.0" -> {
                linters0?.forEach { linter ->
                    allErrors.addAll(linter.lint(tokens))
                }
            }
            else -> {
                throw IllegalArgumentException("Unsupported version: $version")
            }
        }
        return if (allErrors.isEmpty()) {
            emptyList()
        } else {
            allErrors
        }
    }
}