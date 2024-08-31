package org.example

fun main() {
    val formatter = CodeFormatter()
    val configDirectoryPath = "formatter/src/main/resources"
    val codeFilePath = "$configDirectoryPath/code.txt"
    val configFilePath = "$configDirectoryPath/config.json"

    formatter.format(codeFilePath, configFilePath)
}