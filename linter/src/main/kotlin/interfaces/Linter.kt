package com.printscript.linter.interfaces

import com.printscript.token.Token

interface Linter {

    fun lint(tokens: List<Token>): List<String>
}