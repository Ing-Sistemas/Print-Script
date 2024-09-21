package org.example.rules

import org.example.ResultFormatter
import org.example.config.FormatterConfig

class SpaceAroundEquals : CodeFormatRule {
    override fun apply(code: String, config: FormatterConfig, builder: StringBuilder): ResultFormatter {
        if (config.spaceAroundEquals && code == "=") {
            builder.append(code.replace(Regex("([^\\s])=([^\\s])"), "$1 = $2"))
        } else {
            builder.append(code)
        }
        return builder
    }

}