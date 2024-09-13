package org.example.rules

class SingleSpaceBetweenTokens : CodeFormatRule {
    override fun apply(code: String): String {
        return code.replace(Regex("\\s+"), " ")
    }
}