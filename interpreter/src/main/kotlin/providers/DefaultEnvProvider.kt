package providers

import StoredValue
import interfaces.EnvProvider

class DefaultEnvProvider : EnvProvider {
    override fun getEnv(name: String): String? {
        return name
    }
}