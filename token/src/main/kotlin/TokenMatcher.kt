package org.example.token

import org.example.token.stategy.*
import stategy.CallStrategy
import stategy.ClosingParensStrategy
import stategy.OpeningParensStrategy

class TokenMatcher {
    //el orden de como estan las strats importa
    private val strategies = listOf(
        KeywordTokenStrategy(),
        CallStrategy(),
        AssignmentTokenStrategy(),
        SemicolonTokenStrategy(),
        NumberTypeTokenStrategy(),
        StringTypeTokenStrategy(),
        ColonTokenStrategy(),
        NumberTokenStrategy(),
        StringTokenStrategy(),
        OpeningParensStrategy(),
        ClosingParensStrategy(),
        IdentifierTokenStrategy(),
    )

    //TODO esta bien devolver TType? o conviene mas TMatch?
    fun match(input: String, position: Int): TokenMatch? {
        for (strategy in strategies) {
            val isMatch = strategy.match(input, position)
            if (isMatch != null) {
                return isMatch
            }
        }
        return null
    }
}
