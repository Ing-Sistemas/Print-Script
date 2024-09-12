package org.example.rules

class LineJumpAfterSemicolon : CodeFormatRule {
    override fun apply(code: String): String {
        return code.replace(Regex(";[ \t]*\n?"), ";\n")
    }
}