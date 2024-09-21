package org.example.rules

import org.example.config.FormatterConfig

interface CodeFormatRule {
    fun apply(code: String, config: FormatterConfig, builder: StringBuilder): String
}