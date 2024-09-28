package com.printscript.interpreter.interfaces

interface EnvProvider {
    fun getEnv(name: String): String?
}