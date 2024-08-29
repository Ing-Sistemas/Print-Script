import com.sun.tools.javac.parser.Lexer
import configurations.ConfigLoader

fun main() {
    val input = "let myVal: string = 'hello argh 76'"

    val configuration = ConfigLoader.loadConfiguration("linter/src/main/resources/configExample.json")
    println("caseConfiguration: ${configuration.caseConfiguration}")
    // val analyzer = StaticCodeAnalyzer(configuration)

    val listToken = Lexer().tokenize(input)

    // println(configuration)

//    println(analyzer.analyze(listToken))
//    for (elem in listToken) {
//        println("${elem.getType()}: ${elem.getValue()} ")
//    }
}