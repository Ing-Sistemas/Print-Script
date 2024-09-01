package org.example.commands

import Lexer
import Token
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.double
import org.example.Interpreter
import org.example.parser.Parser
import java.io.File

class Execute : CliktCommand() {

    private val fileName by argument(help = "The file to execute")
    private val version by option(help = "The version to execute").double().required()

    override fun run() {
        if (version != 1.0) {
            throw CliktError("Your version '$version' is not supported.")
        }

        val baseDir = File("../cli/src/main/resources")
        val inputFile = File(baseDir, fileName)

        echo("Executing input...")
        try {
            val parser = Parser()
            val interpreter = Interpreter()
            val tokens = mutableListOf<Token>()

            val bufferedReader = inputFile.bufferedReader()
            bufferedReader.use { reader ->
                reader.forEachLine { line ->
                    val lexer = Lexer()
                    val lineTokens = lexer.tokenize(line)
                    tokens.addAll(lineTokens)
                }
            }
            interpreter.interpret(parser.parse(tokens))
        } catch (e: Exception) {
            throw CliktError(e.message)
        }
    }
}