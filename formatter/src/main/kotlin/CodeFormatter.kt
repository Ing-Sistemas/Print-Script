package org.example

import ASTNode
import org.example.config.FormatterConfig

class CodeFormatter {
    fun format(node: ASTNode, config: FormatterConfig): String {
        val builder = StringBuilder()
        val visitor = FormatterVisitorImpl(config, builder)
        node.accept(visitor)
        return builder.toString()
    }
}