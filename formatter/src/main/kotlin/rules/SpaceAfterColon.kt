package org.example.rules

import org.example.config.FormatterConfig

class SpaceAfterColon : CodeFormatRule {
    override fun apply(code: String, config: FormatterConfig, builder: StringBuilder) {
        if (config.spaceAfterColon && code == ":") {
            builder.append("$code ")
        } else {
            builder.append(code)
        }
    }
}