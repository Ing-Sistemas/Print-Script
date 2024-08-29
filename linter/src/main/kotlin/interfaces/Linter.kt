package interfaces

import Token

interface Linter {

    fun lint(tokens: List<Token>): List<String>
}