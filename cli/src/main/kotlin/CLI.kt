package org.example

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import org.example.commands.Analyse
import org.example.commands.Execute
import org.example.commands.Format
import org.example.commands.Validate

class CLI : CliktCommand() {
    override fun run() {
        echo("Enter a command")
    }
}

fun main(args: Array<String>) {
    CLI().subcommands(Execute(), Analyse(), Format(), Validate()).main(args)
}