package com.roguemafia.rogueftc.robot


class Robot {

    internal constructor(builder: Builder) {

    }

    data class Builder(var robotName: String) {
        operator fun invoke(block: Builder.() -> Unit): Robot {
            block
            return Robot(this)
        }
    }
}