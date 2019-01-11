package com.roguemafia.rogueftc.vision.vuforia

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.robotcore.external.ClassFactory
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaRoverRuckus
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener
import java.util.ArrayList

class VuforiaWrapper {

    var vuforia: VuforiaLocalizer
        private set

    var vuforiaInitialized = false
        private set

    var trackingInitialized = false
        private set

    constructor(opMode: OpMode, parameters: VuforiaLocalizer.Parameters) {
        vuforia = ClassFactory.getInstance().createVuforia(parameters)
        vuforiaInitialized = true
    }

}