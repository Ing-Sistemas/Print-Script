package com.printscript.formatter

import com.printscript.formatter.config.FormatterConfig
import com.printscript.formatter.rules.SpaceAroundColon
import com.printscript.formatter.rules.SpaceAroundEquals
import com.printscript.formatter.rules.SpaceAroundOperator

class RuleApplier(private val config: FormatterConfig, private val builder: StringBuilder) {
    fun apply(code: String) {
        when (code) {
            "=" -> {
                if (config.spaceAroundEquals) {
                    SpaceAroundEquals().apply(code, config, builder)
                } else {
                    builder.append(code)
                }
            }
            ":" -> {
                if (config.spaceAfterColon || config.spaceBeforeColon) {
                    SpaceAroundColon().apply(code, config, builder)
                } else {
                    builder.append(code)
                }
            }
//            "println" -> {
//                if (config.lineJumpBeforePrintln > 0) {
//                    LineJumpBeforePrintln(config.lineJumpBeforePrintln).apply(code, config, builder)
//                }
//                builder.append(code)
//            }
            "+", "-", "*", "/" -> {
                if (config.spaceAroundOperators) {
                    SpaceAroundOperator().apply(code, config, builder)
                } else {
                    builder.append(code)
                }
            }
//            ";" -> {
//                if (config.lineJumpAfterSemicolon) {
//                    LineJumpAfterSemicolon().apply(code, config, builder)
//                }
//                builder.append(code)
//            }

            else -> builder.append(code)
        }
    }
}