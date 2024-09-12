package org.example.rules

class LineJumpBeforePrintln(private val lineJumps: Int) : CodeFormatRule {
    override fun apply(code: String): String {
        return code.replace(Regex("(?<!\\n)(.*)(println\\(.*\\))"), "$1\n".repeat(lineJumps) + "$2")
    }
}