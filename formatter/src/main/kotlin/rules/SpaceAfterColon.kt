package com.printscript.formatter.rules

class SpaceAfterColon : CodeFormatRule {
    override fun apply(code: String): String {
        return code.replace(Regex(":([^\\s])"), ": $1")
    }
}