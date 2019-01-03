package com.roguemafia.rogueftc.robot

import com.qualcomm.robotcore.robot.RobotState

interface Subsystem {
    fun init()

    fun active(state: RobotState)

    fun shutdown()
}
