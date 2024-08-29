import com.sun.tools.javac.parser.Lexer
import configurations.ConfigLoader
import linters.StaticCodeAnalyzer

fun main() {
    val input = "let val_derrama: string = 'hello argh 76'; println(val_derrama);"

    val configuration = ConfigLoader.loadConfiguration("linter/src/main/resources/configExample.json")

    val listToken = Lexer().tokenize(input)
    val analyzer = StaticCodeAnalyzer(configuration).analyze(listToken)

    println(analyzer)

//    println(analyzer.analyze(listToken))
//    for (elem in listToken) {
//        println("${elem.getType()}: ${elem.getValue()} ")
//    }
}