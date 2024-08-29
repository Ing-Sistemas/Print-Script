package configurations
import com.google.gson.*
import java.io.FileReader
import java.lang.reflect.Type

object ConfigLoader {
    fun loadConfiguration(filePath: String): Configuration {
        val gson = GsonBuilder()
            .registerTypeAdapter(CaseConfiguration::class.java, CaseConfigurationDeserializer())
            .create()

        FileReader(filePath).use { reader ->
            val returner = gson.fromJson(reader, Configuration::class.java)
            println(returner)
            return returner
        }
    }
}

class CaseConfigurationDeserializer : JsonDeserializer<CaseConfiguration> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): CaseConfiguration {
        val value = json.asString
        return CaseConfiguration.values().firstOrNull { it.value == value }
            ?: throw JsonParseException("Invalid case configuration: $value")
    }
}