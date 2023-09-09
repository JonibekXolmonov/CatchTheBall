package com.catchthisball.catcher.utils

object NonRepeatingRandom {
    private var previous = 1

    private fun retrieve(): Int {

        val rand = (1..4).random()
        return if (rand == previous) {
            retrieve() // recursive call if two subsequent retrieve() calls would return the same number
        } else {
            previous = rand // remember last random number
            rand
        }
    }
}