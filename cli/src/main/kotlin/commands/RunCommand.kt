package org.example.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import java.io.File

class RunCommand : CliktCommand() {

    val src: File by argument(help = "The source file to run").file()

    override fun run() {
        TODO("Implement this command")
    }
}