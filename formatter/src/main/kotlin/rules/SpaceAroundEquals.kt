package com.printscript.formatter.rules

import com.printscript.formatter.config.FormatterConfig

class SpaceAroundEquals : CodeFormatRule {
    override fun apply(code: String, config: FormatterConfig, builder: StringBuilder) {
        if (config.spaceAroundEquals && code == "=") {
            builder.append(" = ")
        } else {
            builder.append(code)
        }
    }
}