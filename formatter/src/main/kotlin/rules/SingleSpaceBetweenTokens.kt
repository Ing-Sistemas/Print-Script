package org.example.rules

import org.example.config.FormatterConfig

class SingleSpaceBetweenTokens : CodeFormatRule {
    override fun apply(code: String, config: FormatterConfig, builder: StringBuilder) {
        // todo() esto si la rule esta activa, tiene q hacerse para cada nodo, es decir,
        // iterar the whole el builder.
        if (config.singleSpaceBetweenTokens && code == " ") {
            builder.append(code.replace(Regex("\\s+"), " "))
        } else {
            builder.append(code)
        }
    }

}