package com.roguemafia.rogueftc.robot

import com.qualcomm.robotcore.robot.RobotState
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

interface Subsystem {
    fun init()

    fun active(state: RobotState)

    fun shutdown()
}

data class SubsystemCommand(val commandType: String, val command: JvmType.Object)