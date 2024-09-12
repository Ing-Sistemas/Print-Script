package org.example

object TokenRegex {
    val identifierRegex = Regex("[a-zA-Z_][a-zA-Z_0-9]*")
    val keywordRegex = Regex("\\b(let|var)\\b")
    val plusOperatorRegex = Regex("\\+")
    val minusOperatorRegex = Regex("-")
    val multiplyOperatorRegex = Regex("\\*")
    val divideOperatorRegex = Regex("/")
    val functionCallRegex = Regex("\\b(if|else|for|while|println)\\b")
    val opening_parens = Regex("\\(")
    val closing_parens = Regex("\\)")
    val stringTypeRegex = Regex("string")
    val numberTypeRegex = Regex("number")
    val assignmentRegex = Regex("=")
    val semicolonRegex = Regex(";")
    val colonRegex = Regex(":")
    val literalNumberRegex = Regex("\\d+(\\.\\d+)?")
    val literalStringRegex = Regex("\".*?\"|'.*?'")
}