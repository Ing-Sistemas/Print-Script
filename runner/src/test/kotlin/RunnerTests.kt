import org.example.Runner
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import utils.Storage
import java.io.File
import kotlin.test.Test

class RunnerTests {

    @Test
    fun `runner test`(){
        val runner = Runner()
        val inputFile = File("../runner/src/test/resources", "main.ps")
        val bufferedReader = inputFile.bufferedReader()
        bufferedReader.use { reader ->
            reader.forEachLine { line ->
                assertDoesNotThrow { runner.run(line, "1.0")  }
            }
        }
    }
}