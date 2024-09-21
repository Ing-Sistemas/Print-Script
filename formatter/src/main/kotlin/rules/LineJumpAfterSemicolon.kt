package org.example.rules

import org.example.config.FormatterConfig

class LineJumpAfterSemicolon : CodeFormatRule {
    override fun apply(code: String, config: FormatterConfig, builder: StringBuilder) {
        if (config.lineJumpAfterSemicolon && code == ";") {
            builder.append(";\n")
        } else {
            builder.append(code)
        }
    }
}