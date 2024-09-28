package com.printscript.formatter.rules

class SpaceAroundOperator : CodeFormatRule {
    override fun apply(code: String): String {
        return code.replace(Regex("([^\\s])([+\\-*/])([^\\s])"), "$1 $2 $3")
    }
}