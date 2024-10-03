
import com.printscript.interpreter.providers.DefaultEnvProvider
import com.printscript.interpreter.providers.DefaultInputProvider
import com.printscript.interpreter.providers.DefaultOutPutProvider
import com.printscript.interpreter.results.InterpreterFailure
import com.printscript.interpreter.results.InterpreterSuccess
import com.printscript.runner.Runner
import java.io.File
import kotlin.test.Test

class RunnerTests {

    @Test
    fun `runner test`() {
        val file = File("../runner/src/test/resources/main.ps")
        val runner = Runner(
            DefaultInputProvider(),
            DefaultOutPutProvider(),
            DefaultEnvProvider(),
        ).run(file.inputStream(), "1.1")
        assert(runner is InterpreterSuccess)
    }

    @Test
    fun `runner fail test`() {
        val file = File("../runner/src/test/resources/fail.ps")
        val runner = Runner(
            DefaultInputProvider(),
            DefaultOutPutProvider(),
            DefaultEnvProvider(),
        ).run(file.inputStream(), "1.1")
        assert(runner is InterpreterFailure)
    }
}