import org.example.CodeFormatter
import org.example.config.FormatterConfig


fun main() {
    val config = FormatterConfig(
        true,
        true,
        true,
        1
    )
    val assignment = AssignmentStatement(
        IdentifierExpression("a", Position(6,1)),
        "=",
        NumberLiteral(5.0, Position(7,1)),
        Position(9,1)
    )
    val ast = VariableDeclarationStatement(
        "val",
        TypeDeclarationExpression("number", Position(4, 1)),
        assignment,
        Position(0,1)
    )
    println(CodeFormatter().format(ast, config))
}
