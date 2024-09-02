

sealed interface ASTNode{
    fun <T> accept(visitor: Visitor<T>): T
}