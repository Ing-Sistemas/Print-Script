package com.printscript.formatter.rules

class SpaceBeforeColonRule : CodeFormatRule {
    override fun apply(code: String): String {
        return code.replace(Regex("([^\\s]):"), "$1 :")
    }
}