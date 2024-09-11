package org.example.rules

interface CodeFormatRule {
    fun apply(code: String): String
}