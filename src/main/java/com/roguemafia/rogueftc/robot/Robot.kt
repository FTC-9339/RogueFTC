package com.roguemafia.rogueftc.robot

class Robot {

    internal constructor() {

    }

    class Builder {
        var isBenchTest: Boolean = false

        fun createRobot(): Robot {
            return Robot()
        }
    }
}