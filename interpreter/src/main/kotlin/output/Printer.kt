package org.example.output

import interfaces.OutPut
import utils.Storage

class Printer : OutPut {
    override fun output(code: Any) {
        println(code)
    }
}