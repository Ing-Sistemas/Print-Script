package org.example

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import org.example.commands.ExecuteCommand

class CLI : CliktCommand() {

    override fun run() {
        echo("enter a command")
    }
}

fun main(args: Array<String>) {
    CLI().subcommands(ExecuteCommand()).main(args)
}