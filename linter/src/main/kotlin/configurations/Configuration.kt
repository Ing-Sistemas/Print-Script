package com.printscript.linter.configurations

import configurations.IdentifierFormat

data class Configuration(
    val identifier_format: IdentifierFormat,
    val `mandatory-variable-or-literal-in-println`: Boolean,
    val `mandatory-variable-or-literal-in-readInput`: Boolean,
)