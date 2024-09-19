
class ReadInputNode(
    private val input: String,
    private val position: Position,
) : ASTNode {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }

    fun getInput(): String = input
    fun getPosition(): Position = position
}