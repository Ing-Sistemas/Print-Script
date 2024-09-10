package org.example.token

import org.example.token.stategy.*
import stategy.*
import stategy.operators.DivideTokenStrategy
import stategy.operators.MinusTokenStrategy
import stategy.operators.MultiplyTokenStrategy
import stategy.operators.PlusTokenStrategy

class TokenMatcher {
    /**
     * Order of elements in [strategies] matters, therefore variable names are last
     * (so all the reserved strings are tokenized first)
     */

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
        OpeningCurlyBracks(),
        ClosingCurlyBracks(),
        PlusTokenStrategy(),
        MinusTokenStrategy(),
        DivideTokenStrategy(),
        MultiplyTokenStrategy(),
        IdentifierTokenStrategy(),
    )

    /**
     * goes through every strategy and returns the one that matches the given input
     */

    // TODO esta bien devolver TType? o conviene mas TMatch?
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