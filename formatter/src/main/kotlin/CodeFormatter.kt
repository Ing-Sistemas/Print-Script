package org.example

import org.example.config.FormatterConfig
import org.example.config.JsonReader

class CodeFormatter {
    // lee el json de la config y dependiendo de que value
    // encuentra entra en un if o en el else (pq muchas son booleanas)
    // write into file (override previous)
    // todo leer el .txt con el codigo a formatear
    private val config = JsonReader().convertJsonIntoConfig("config.json")

    fun format(code: String, config: FormatterConfig): String {
        val formattedCode = StringBuilder()

        // todo

        return formattedCode.toString()
    }
}