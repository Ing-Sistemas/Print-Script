

import com.google.gson.*
import java.lang.reflect.Type

class ASTNodeDeserializer() : JsonDeserializer<ASTNode> {
    override fun deserialize(p0: JsonElement?, p1: Type?, p2: JsonDeserializationContext?): ASTNode {
        val gson = Gson()
        TODO()
    }
}