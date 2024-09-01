package org.example.commands

import Lexer
import Token
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import org.example.Interpreter
import org.example.parser.Parser
import kotlin.system.exitProcess

class ExecuteCommand : CliktCommand() {
    private val inputFile by argument(help = "The file to process").file(mustExist = true)
    override fun run() {
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
            System.err.println(e.message)
            exitProcess(1)
        }
    }
}