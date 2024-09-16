package org.example

import java.io.InputStream
import java.util.Scanner
import kotlin.io.path.fileVisitor

class ReaderIterator {

    fun getLineIterator(inputStream: InputStream): Iterator<String> {
        val scanner = Scanner(inputStream)

        return object : Iterator<String> {
            var nextLine: String? = advance()

            override fun hasNext(): Boolean {
                return nextLine != null
            }

            override fun next(): String {
                val currentLine = nextLine ?: throw NoSuchElementException()
                nextLine = advance()
                return currentLine
            }
            private fun advance(): String? {
                return if (scanner.hasNextLine()) {
                    scanner.nextLine()
                } else {
                    null
                }
            }
        }
    }
}