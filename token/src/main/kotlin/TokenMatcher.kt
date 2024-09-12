package org.example

import org.example.strategy.*
import org.example.strategy.operators.DivideTokenStrategy
import org.example.strategy.operators.MinusTokenStrategy
import org.example.strategy.operators.MultiplyTokenStrategy
import org.example.strategy.operators.PlusTokenStrategy

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
            if (isMatch != null) return isMatch
        }
        return null
    }
}