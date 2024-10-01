package com.printscript.linter.configurations
import com.google.gson.*
import configurations.IdentifierFormat
import java.io.FileReader
import java.lang.reflect.Type

object ConfigLoader {
    fun loadConfiguration(filePath: String): Configuration {
        val gson = GsonBuilder()
            .registerTypeAdapter(IdentifierFormat::class.java, CaseConfigurationDeserializer())
            .create()

        FileReader(filePath).use { reader ->
            val returner = gson.fromJson(reader, Configuration::class.java)
            return returner
        }
    }
}

class CaseConfigurationDeserializer : JsonDeserializer<IdentifierFormat> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): IdentifierFormat {
        val value = json.asString
        return IdentifierFormat.values().firstOrNull { it.value == value }
            ?: throw JsonParseException("Invalid case configuration: $value")
    }
}