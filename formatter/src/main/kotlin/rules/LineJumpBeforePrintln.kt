// package org.example.rules
//
// import org.example.config.FormatterConfig
//
// class LineJumpBeforePrintln(private val lineJumps: Int) : CodeFormatRule {
//    override fun apply(code: String, config: FormatterConfig, builder: StringBuilder) {
//        if (config.lineJumpBeforePrintln > 0 && code == "println") {
//            builder.append("\n".repeat(lineJumps))
//        }
//        builder.append(code)
//    }
//
// }