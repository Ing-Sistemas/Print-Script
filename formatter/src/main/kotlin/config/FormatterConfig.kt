package org.example.config

data class FormatterConfig(

    val spaceBeforeColon: Boolean,
    val spaceAfterColon: Boolean,
    val spaceAroundEquals: Boolean,
    val lineJumpBeforePrintln: Int,
    val lineJumpAfterSemicolon: Boolean = true,
    val singleSpaceBetweenTokens: Boolean = true,
    val spaceAroundOperators: Boolean = true,
)