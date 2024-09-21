package org.example.rules

import org.example.config.FormatterConfig

class LineJumpAfterSemicolon : CodeFormatRule {
    override fun apply(code: String, config: FormatterConfig, builder: StringBuilder) {
        if (config.lineJumpAfterSemicolon && code == ";") {
            builder.append(code.replace(Regex("([^\\s]);([^\\s])"), "$1;\n$2"))
        } else {
            builder.append(code)
        }
    }
}