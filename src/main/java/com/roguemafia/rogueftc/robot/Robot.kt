package com.roguemafia.rogueftc.robot


class Robot {

    internal constructor() {

    }

    class Builder {
        var isBenchTest: Boolean = false

        fun create(lambda: Builder.() -> Unit): Robot {
            lambda
            return Robot()
        }
    }
}