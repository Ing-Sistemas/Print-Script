
class Position(private val column: Int, private val line: Int) {
    fun getLine() = line // line in file
    fun getColumn() = column // starting index of token
}