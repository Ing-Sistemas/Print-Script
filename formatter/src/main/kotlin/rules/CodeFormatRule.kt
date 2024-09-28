package com.printscript.formatter.rules

interface CodeFormatRule {
    fun apply(code: String): String
}