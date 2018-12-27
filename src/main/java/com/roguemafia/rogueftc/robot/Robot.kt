package com.roguemafia.rogueftc.robot

open class Robot {



    companion object Builder {
        var isBenchTest: Boolean = false

        fun create(): Robot {
            return Robot()
        }
    }
}