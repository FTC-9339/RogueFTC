package com.roguemafia.rogueftc.opmode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.roguemafia.rogueftc.control.JoypadConfig

abstract class TeleOP : LinearOpMode() {

    override fun runOpMode() {

        startup()
        waitForStart()
        while (opModeIsActive()) {
            opModeBehavior()
        }
        shutdown()
    }

    protected abstract fun startup()

    protected abstract fun opModeBehavior()

    protected abstract fun shutdown()
}