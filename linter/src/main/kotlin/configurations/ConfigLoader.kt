package configurations
import com.google.gson.Gson
import java.io.FileReader

object ConfigLoader {

    fun loadConfiguration(filePath: String): Configuration {
        val gson = Gson()
        FileReader(filePath).use { reader ->
            return gson.fromJson(reader, Configuration::class.java)
        }
    }
}