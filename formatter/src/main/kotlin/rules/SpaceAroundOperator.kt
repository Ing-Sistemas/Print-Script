package com.printscript.formatter.rules

import com.printscript.formatter.config.FormatterConfig

class SpaceAroundOperator : CodeFormatRule {
    override fun apply(code: String, config: FormatterConfig, builder: StringBuilder) {
        val isOperator = code == "+" || code == "-" || code == "*" || code == "/"
        if (config.spaceAroundOperators && isOperator) {
            builder.append(" $code ")
        } else {
            builder.append(code)
        }
    }
}