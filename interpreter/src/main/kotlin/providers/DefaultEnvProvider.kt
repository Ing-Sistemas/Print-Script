package providers

import interfaces.EnvProvider

class DefaultEnvProvider : EnvProvider {
    override fun getEnv(name: String): String? {
        return name
    }
}