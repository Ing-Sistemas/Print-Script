package com.printscript.formatter.rules

import com.printscript.formatter.config.FormatterConfig

class SpaceAroundColon : CodeFormatRule {
    override fun apply(code: String, config: FormatterConfig, builder: StringBuilder) {
        val spaceAfterColon = config.spaceAfterColon
        val spaceBeforeColon = config.spaceBeforeColon
        if (spaceAfterColon != null && spaceAfterColon && spaceBeforeColon != null && spaceBeforeColon && code == ":") {
            builder.append(" $code ")
        } else if (spaceAfterColon != null && spaceAfterColon && code == ":") {
            builder.append("$code ")
        } else if (spaceBeforeColon != null && spaceBeforeColon && code == ":") {
            builder.append(" $code")
        } else {
            builder.append(code)
        }
    }
}