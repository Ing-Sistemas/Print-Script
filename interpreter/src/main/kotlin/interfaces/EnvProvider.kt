package interfaces

interface EnvProvider {
    fun getEnv(name: String): String?
}