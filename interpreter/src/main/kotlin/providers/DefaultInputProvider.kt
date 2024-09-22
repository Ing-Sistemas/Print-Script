package providers

import interfaces.InputProvider

class DefaultInputProvider : InputProvider {
    override fun readInput(name: String): String {
        val input = readlnOrNull() ?: ""
        return input
    }
}