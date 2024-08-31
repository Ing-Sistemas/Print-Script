package org.example

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.transformValues
import com.github.ajalt.clikt.parameters.options.versionOption
import java.io.PrintWriter

class CLI : CliktCommand() {
    val v by option(help = "two vals").transformValues(2) {it[0] to it[1]}
    init {
        versionOption("1.0")
    }

    override fun run() {
        echo("Starting example CLI")
    }
}