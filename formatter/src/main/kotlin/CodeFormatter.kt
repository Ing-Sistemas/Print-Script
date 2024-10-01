package com.printscript.formatter

import com.printscript.ast.ASTNode
import com.printscript.formatter.config.FormatterConfig

class CodeFormatter {
    fun format(node: ASTNode, config: FormatterConfig): String {
        val builder = StringBuilder()
        val visitor = FormatterVisitorImpl(config, builder)
        node.accept(visitor)
        return builder.toString()
    }
}