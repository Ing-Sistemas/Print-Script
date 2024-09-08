package linters

import Token
import configurations.CaseConfiguration
import interfaces.Linter

class ReadInputRestrictions(private val caseConfiguration: CaseConfiguration) : Linter {
    override fun lint(tokens: List<Token>): List<String> {
        val errors = mutableListOf<String>()
        TODO("Not yet implemented")
    }
}