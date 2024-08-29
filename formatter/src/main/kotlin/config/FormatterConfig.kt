package org.example.config


data class FormatterConfig(
    val printAllowsExpression: Boolean = true,
    val identifierFormat: String = "camel case" // options: "camel case" or "snake case"
)