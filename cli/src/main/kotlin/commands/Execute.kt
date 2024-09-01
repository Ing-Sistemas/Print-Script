package org.example.commands

import Lexer
import Token
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import org.example.CLIContext
import org.example.Interpreter
import org.example.parser.Parser
import java.io.File

class Execute : CliktCommand(
    name = "execute",
    help = "Executes a command",
) {

    override fun run() {
        val cliContext = currentContext.findObject<CLIContext>() ?: throw CliktError("Could not find CLIContext")
        val fileName = cliContext.fileName

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