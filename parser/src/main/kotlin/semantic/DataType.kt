package org.example.parser.semantic

enum class DataType(private val typeName: String) {
    STRING("string"),
    NUMBER("number"),
    BOOLEAN("boolean"),
    ;

    override fun toString(): String {
        return typeName
    }
}