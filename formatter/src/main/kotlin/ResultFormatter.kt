package org.example

class ResultFormatter() {
    private val builder = StringBuilder()

    fun append(code: String) {
        builder.append(code)
    }

    fun appendLine(code: String) {
        builder.append(code)
        builder.append("\n")
    }

    fun getResult(): String {
        return builder.toString()
    }
}