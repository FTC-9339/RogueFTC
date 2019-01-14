package com.roguemafia.rogueftc.robot


class Robot {

    internal constructor(builder: Builder) {

    }

     class Builder {
        operator fun invoke(block: Builder.() -> Unit): Robot {
            this.block()
            return Robot(this)
        }
    }
}