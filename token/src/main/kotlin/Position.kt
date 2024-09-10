
class Position(private val line: Int, private val column: Int ) {
    fun getLine() = line //line in file
    fun getColumn() = column //starting index of token
}
