package org.example.rules

import org.example.config.FormatterConfig

class SpaceBeforeColon : CodeFormatRule {
    override fun apply(code: String, config: FormatterConfig, builder: StringBuilder) {
        if (config.spaceBeforeColon && code == ":") {
            builder.append(" $code")
        } else {
            builder.append(code)
        }
    }
}