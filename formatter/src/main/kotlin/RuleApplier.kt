import org.example.config.FormatterConfig
import org.example.rules.*
import java.lang.StringBuilder

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