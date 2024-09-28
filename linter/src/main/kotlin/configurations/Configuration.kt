package com.printscript.linter.configurations

data class Configuration(
    val caseConfiguration: CaseConfiguration,
    val restrictPrintln: Boolean,
    val readInput: Boolean,
)