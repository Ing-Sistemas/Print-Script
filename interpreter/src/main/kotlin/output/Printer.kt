package org.example.output

import interfaces.OutPut

class Printer : OutPut {
    override fun output(code: Any) {
        println(code)
    }
}