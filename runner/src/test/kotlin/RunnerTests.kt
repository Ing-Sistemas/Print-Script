import org.example.Runner
import java.io.File
import kotlin.test.Test

class RunnerTests {

    @Test
    fun `runner test`() {
        val file = File("../runner/src/test/resources/main.ps")
        val runner = Runner().run(file.inputStream(), "1.0")
    }
}