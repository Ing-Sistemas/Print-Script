import org.example.Runner
import providers.DefaultEnvProvider
import providers.DefaultInputProvider
import providers.DefaultOutPutProvider
import java.io.File
import kotlin.test.Test

class RunnerTests {

    @Test
    fun `runner test`() {
        val file = File("../runner/src/test/resources/main.ps")
        val runner = Runner(DefaultInputProvider(), DefaultOutPutProvider(), DefaultEnvProvider()).run(file.inputStream(), "1.0")
    }
}