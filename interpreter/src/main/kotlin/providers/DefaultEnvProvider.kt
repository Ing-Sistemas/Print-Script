package com.printscript.interpreter.providers

import com.printscript.interpreter.interfaces.EnvProvider

class DefaultEnvProvider : EnvProvider {
    override fun getEnv(name: String): String? {
        return System.getenv(name)
    }
}