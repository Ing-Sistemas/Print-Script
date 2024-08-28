package linters

import configurations.Configuration
import interfaces.Linter

class Linter(private val configuration: Configuration) : Linter {

    override fun lint(code: String) {
        when (configuration.caseConfiguration.value) {
            "Camel Case" -> checkCamelCase(code)
            "Snake Case" -> checkSnakeCase(code)
        }

        if (configuration.restrictPrintln) {
            checkPrintln(code)
        }
    }

    private fun checkCamelCase(code: String) {
        TODO()
    }

    private fun checkSnakeCase(code: String) {
        TODO()
    }

    private fun checkPrintln(code: String) {
        TODO()
    }
}