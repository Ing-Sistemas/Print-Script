package semantic

import org.example.LiteralNode
import kotlin.test.Test

class TestCases {

    @Test
    fun `valid ast gives valid output`() {
        val ast = LiteralNode("30", "number", 0, 2) // necesito un ast
        val semanticAnalyzer = SemanticAnalyzer()
        val result = semanticAnalyzer.analyze(ast)
        assert(result.getValue() == "30")
    }
}