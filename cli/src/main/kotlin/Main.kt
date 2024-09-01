package org.example

import java.io.File

fun main() {
    val file = File("cli/src/main/resources/execute.ps")
    file.bufferedWriter().use { writer ->
        for (i in 1..1000)
            writer.write("println($i);")
    }
}