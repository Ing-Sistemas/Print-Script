package interfaces

import org.example.Token

interface Linter {

    fun lint(tokens: List<Token>): List<String>
}