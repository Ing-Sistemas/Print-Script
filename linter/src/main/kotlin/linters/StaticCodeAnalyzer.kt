package com.printscript.linter.linters

import com.printscript.linter.configurations.Configuration
import com.printscript.linter.interfaces.Linter
import com.printscript.token.Token

class StaticCodeAnalyzer(private val configuration: Configuration?, private val version: String) {

    private val linters0: List<Linter>? = configuration?.let {
        listOfNotNull(
            it.identifier_format?.let { format -> IdentifierCaseLinter(format) },
            if (it.`mandatory-variable-or-literal-in-println`) PrinterRestrictions() else null,
        ).takeIf { linters -> linters.isNotEmpty() }
    }

    private val linters1: List<Linter>? = configuration?.let {
        listOfNotNull(
            it.identifier_format?.let { format -> IdentifierCaseLinter(format) },
            if (it.`mandatory-variable-or-literal-in-println`) PrinterRestrictions() else null,
            if (it.`mandatory-variable-or-literal-in-readInput`) ReadInputRestrictions() else null,
        ).takeIf { linters -> linters.isNotEmpty() }
    }

    fun analyze(tokens: List<Token>): List<String> {
        val allErrors = mutableListOf<String>()
        if (configuration == null) {
            return emptyList()
        }
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