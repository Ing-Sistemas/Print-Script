package org.example.config

import com.google.gson.Gson
import java.io.File

class JsonReader {
    fun convertJsonIntoConfig(filepath: String): FormatterConfig {
        val gson = Gson()
        val jsonString = File(filepath).readText()
        return gson.fromJson(jsonString, FormatterConfig::class.java)
    }
}