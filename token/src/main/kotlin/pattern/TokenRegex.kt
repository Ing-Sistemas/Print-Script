package org.example.token.pattern

object TokenRegex {
    val identifierRegex = Regex("[a-zA-Z_][a-zA-Z_0-9]*")
    val keywordRegex = Regex("\\b(let|var|if|else|for|while|println)\\b")
    val plusOperatorRegex = Regex("\\+")
    val minusOperatorRegex = Regex("-")
    val multiplyOperatorRegex = Regex("\\*")
    val divideOperatorRegex = Regex("/")
    val stringTypeRegex = Regex("string")
    val numberTypeRegex = Regex("number")
    val assignmentRegex = Regex("=")
    val semicolonRegex = Regex(";")
    val colonRegex = Regex(":")
    val literalNumberRegex = Regex("\\d+(\\.\\d+)?")
    val literalStringRegex = Regex("\".*?\"|'.*?'")
}
