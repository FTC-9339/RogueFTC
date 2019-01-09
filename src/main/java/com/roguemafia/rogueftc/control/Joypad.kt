package com.roguemafia.rogueftc.control

import com.qualcomm.robotcore.hardware.Gamepad
import kotlin.math.abs

/**
 * Simple data class providing the user the ability to change deadzone as well as a callback upon the event of a change in state.
 */
data class JoypadConfig (
        val joystickDeadzone: Double = 0.2,
        val triggerDeadzone: Double = 0.0,
        val callback: (JoypadState) -> Unit = {}
)

class Joypad(private var config: JoypadConfig = JoypadConfig()) {

    fun updateFromGamepad(gamepad: Gamepad): JoypadState {
        if (previousGamepad == gamepad) return currentState

        previousGamepad = gamepad

        currentState = gamepad.run {
            JoypadState(
                    left_stick = currentState.left_stick.update(
                            left_stick_x.toDouble(),
                            left_stick_y.toDouble(),
                            left_stick_button,
                            config.joystickDeadzone
                    ),
                    right_stick = currentState.right_stick.update(
                            right_stick_x.toDouble(),
                            right_stick_y.toDouble(),
                            right_stick_button,
                            config.joystickDeadzone
                    ),
                    faceButtons = currentState.faceButtons.update(
                            a,
                            b,
                            x,
                            y
                    ),
                    dPadButtons = currentState.dPadButtons.update(
                            dpad_up,
                            dpad_down,
                            dpad_left,
                            dpad_right
                    ),
                    triggerBumperState = currentState.triggerBumperState.update(
                            left_trigger.toDouble(),
                            right_trigger.toDouble(),
                            left_bumper,
                            right_bumper,
                            config.triggerDeadzone
                    )
            )
        }

        config.callback(currentState)

        return currentState
    }

    private var previousGamepad = Gamepad()

    private var currentState = JoypadState()

    fun changeConfig(newConfig: JoypadConfig) {config = newConfig}

    fun getCurrentState() = currentState
}
data class JoypadState(
        val left_stick: JoystickState = JoystickState(),
        val right_stick: JoystickState = JoystickState(),
        val dPadButtons: DPadState = DPadState(),
        val faceButtons: FaceButtonState = FaceButtonState(),
        val triggerBumperState: TriggerBumperState = TriggerBumperState()
)

data class JoystickState(
        val x: Double = 0.0,
        val y: Double = 0.0,
        val clickyBoi: ButtonState = ButtonState.NOT_PRESSED,
        val active: Boolean = false
) {
    fun update(x: Double, y: Double, clickyBoi: Boolean, deadzone: Double) =
            JoystickState(
                    x = if(abs(x) < deadzone) {
                        0.0
                    } else {
                        x
                    },
                    y = if(abs(y) < deadzone) {
                        0.0
                    } else {
                        y
                    },
                    clickyBoi = this.clickyBoi.update(clickyBoi),
                    active = x != 0.0 || y != 0.0 || clickyBoi
            )
}

enum class ButtonState {
    NOT_PRESSED, PRESSED, HELD;

    fun update(button: Boolean): ButtonState {
        when (button) {
            true -> if (this == NOT_PRESSED) {
                return PRESSED
            } else if (this == PRESSED) {
                return HELD
            }
            false -> return NOT_PRESSED
        }
        return HELD
    }
}

data class FaceButtonState(
    val a: ButtonState = ButtonState.NOT_PRESSED,
    val b: ButtonState = ButtonState.NOT_PRESSED,
    val x: ButtonState = ButtonState.NOT_PRESSED,
    val y: ButtonState = ButtonState.NOT_PRESSED
) {
    fun update(a: Boolean, b: Boolean, x: Boolean, y: Boolean) =
            FaceButtonState(
                    a = this.a.update(a),
                    b = this.b.update(b),
                    x = this.x.update(x),
                    y = this.y.update(y)
            )
}

data class DPadState(
    val up: ButtonState = ButtonState.NOT_PRESSED,
    val down: ButtonState = ButtonState.NOT_PRESSED,
    val left: ButtonState = ButtonState.NOT_PRESSED,
    val right: ButtonState = ButtonState.NOT_PRESSED
) {
    fun update(up: Boolean, down: Boolean, left: Boolean, right: Boolean) =
            DPadState(
                    up = this.up.update(up),
                    down = this.down.update(down),
                    left = this.left.update(left),
                    right = this.right.update(right)
            )
}

data class TriggerState(
    val value: Double = 0.0,
    val deadzone: Double = 0.0
) {
    fun update(value: Double, deadzone: Double) =
            TriggerState(
                    value = if (abs(value) < deadzone) 0.0 else value,
                    deadzone = deadzone
            )
}

data class TriggerBumperState(
    val left_trigger: TriggerState = TriggerState(),
    val right_trigger: TriggerState = TriggerState(),
    val leftBumper: ButtonState = ButtonState.NOT_PRESSED,
    val rightBumper: ButtonState = ButtonState.NOT_PRESSED
) {
    fun update(left_trigger: Double, right_trigger: Double, leftBumper: Boolean, rightBumper: Boolean, triggerDeadzone: Double) =
            TriggerBumperState(
                    left_trigger = this.left_trigger.update(left_trigger, triggerDeadzone),
                    right_trigger = this.right_trigger.update(right_trigger, triggerDeadzone),
                    leftBumper = this.leftBumper.update(leftBumper),
                    rightBumper = this.rightBumper.update(rightBumper)
            )
}