package interfaces

import StoredValue

interface EnvProvider {
    fun getEnv(name: String): String?
}