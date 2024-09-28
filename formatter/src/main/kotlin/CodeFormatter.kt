package com.printscript.formatter

import com.printscript.formatter.config.FormatterConfig
import com.printscript.formatter.config.JsonReader
import com.printscript.formatter.rules.*
import java.io.File

class CodeFormatter {
    fun format(codeFilePath: String, configFilePath: String) {
        val config = JsonReader().convertJsonIntoFormatterConfig(configFilePath)
        val code = File(codeFilePath).readText()
        val formattedCode = formatCode(code, config)
        File(codeFilePath).writeText(formattedCode)
    }

    private fun formatCode(code: String, config: FormatterConfig): String {
        val rules = mutableListOf<CodeFormatRule>()

        if (config.spaceBeforeColon) rules.add(SpaceBeforeColonRule())
        if (config.spaceAfterColon) rules.add(SpaceAfterColon())
        if (config.spaceAroundEquals) rules.add(SpaceAroundEquals())
        if (config.lineJumpBeforePrintln > 0) rules.add(LineJumpBeforePrintln(config.lineJumpBeforePrintln))
        if (config.lineJumpAfterSemicolon) rules.add(LineJumpAfterSemicolon())
        if (config.singleSpaceBetweenTokens) rules.add(SingleSpaceBetweenTokens())
        if (config.spaceAroundOperators) rules.add(SpaceAroundOperator())

        return rules.fold(code) { acc, rule -> rule.apply(acc) }
    }
}