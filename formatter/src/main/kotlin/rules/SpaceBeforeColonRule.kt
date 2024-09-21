package org.example.rules

import org.example.config.FormatterConfig

class SpaceBeforeColonRule : CodeFormatRule {
    override fun apply(code: String, config: FormatterConfig, builder: StringBuilder) {
        if (config.spaceBeforeColon && code == ":") {
            builder.append(code.replace(Regex("([^\\s]):([^\\s])"), "$1 :$2"))
        } else {
            builder.append(code)
        }
    }
}