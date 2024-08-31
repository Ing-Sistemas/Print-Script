package org.example

import org.example.config.FormatterConfig
import org.example.config.JsonReader
import java.io.File

class CodeFormatter {
    // lee el json de la config y dependiendo de que value
    // encuentra entra en un if o en el else (pq muchas son booleanas)
    // write into file (override previous)
    // todo leer el .txt con el codigo a formatear

    fun format(codeFilePath: String, configFilePath: String) {
        val config = JsonReader().convertJsonIntoFormatterConfig(configFilePath)
        val code = File(codeFilePath).readText()
        val formattedCode = formatCode(code, config)
        File(codeFilePath).writeText(formattedCode)
    }

    private fun formatCode(code: String, config: FormatterConfig): String {
        var formattedCode = code

        if (config.spaceBeforeColon) {
            formattedCode = formattedCode.replace(Regex("([^\\s]):"), "$1 :")
        }

        if (config.spaceAfterColon) {
            formattedCode = formattedCode.replace(Regex(":([^\\s])"), ": $1")
        }

        if (config.spaceAroundEquals) {
            formattedCode = formattedCode.replace(Regex("([^\\s])=([^\\s])"), "$1 = $2")
        }

        if (config.lineJumpBeforePrintln > 0) {
            formattedCode = formattedCode.replace(Regex("(.*)(println\\(.*\\))"), "$1\n".repeat(config.lineJumpBeforePrintln) + "$2")
        }

        if (config.lineJumpAfterSemicolon) {
            formattedCode = formattedCode.replace(Regex(";"), ";\n")
        }

        if (config.singleSpaceBetweenTokens) {
            formattedCode = formattedCode.replace(Regex("\\s+"), " ")
        }

        if (config.spaceAroundOperators) {
            formattedCode = formattedCode.replace(Regex("([^\\s])([+\\-*/])([^\\s])"), "$1 $2 $3")
        }

        return formattedCode
    }
}