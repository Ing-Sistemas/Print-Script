package org.example.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import org.example.Runner
import java.io.File
import kotlin.system.exitProcess

class RunCommand : CliktCommand() {

    private val sourceFile: String by argument(help = "The source file to RUN")
    override fun run() {
        try {
            val runner = Runner()
            val bufferedReader = File(sourceFile).bufferedReader()
            bufferedReader.use { reader ->
                var line = reader.readLine()
                while (line != null) {
                    runner.run(line)
                    line = reader.readLine()
                }
            }
        } catch (e: Exception) {
            System.err.println(e.message)
            exitProcess(1)
        }
    }
}