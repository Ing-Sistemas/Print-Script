package com.printscript.formatter.rules

import com.printscript.formatter.config.FormatterConfig

interface CodeFormatRule {
    fun apply(code: String, config: FormatterConfig, builder: StringBuilder)
}