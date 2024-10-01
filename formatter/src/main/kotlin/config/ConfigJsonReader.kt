package com.printscript.formatter.config

import com.google.gson.Gson
import java.io.File

class ConfigJsonReader {
    fun convertJsonIntoFormatterConfig(filepath: String): FormatterConfig {
        val gson = Gson()
        val jsonString = File(filepath).readText()
        return gson.fromJson(jsonString, FormatterConfig::class.java)
    }
}