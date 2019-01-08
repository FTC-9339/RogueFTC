package com.roguemafia.rogueftc.control

import com.qualcomm.robotcore.hardware.Gamepad
import kotlin.math.abs

/**
 * Simple data class providing the user the ability to change deadzone as well as a callback upon the event of a change in state.
 */
data class JoypadConfig (
        val joystickDeadzone: Double = 0.2,
        val triggerDeadzone: Double = 0.0,
        val callback: JoypadState.() -> Unit = {}
)

class Joypad(private var config: JoypadConfig = JoypadConfig()) {
    
    fun updateFromGamepad(gamepad: Gamepad) {
        val previousState = currentState

        currentState = gamepad.run {
            JoypadState(
                    left_stick = JoystickState(
                            x = left_stick_x.toDouble(),
                            y = left_stick_y.toDouble(),
                            clickyBoi = currentState.left_stick.clickyBoi.update(left_stick_button)
                    ),
                    right_stick = JoystickState(
                            x = right_stick_x.toDouble(),
                            y = right_stick_y.toDouble(),
                            clickyBoi = currentState.right_stick.clickyBoi.update(right_stick_button)
                    ),
                    dPadButtons =
            )
        }
    }

    private var currentState: JoypadState = JoypadState()

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
        val clickyBoi: ButtonState = ButtonState.NOT_PRESSED
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
                    clickyBoi = this.clickyBoi.update(clickyBoi)
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
                    value = if (value < deadzone) 0.0 else value,
                    deadzone = deadzone
            )
}

data class TriggerBumperState(
    val left_trigger: TriggerState = TriggerState(),
    val right_trigger: TriggerState = TriggerState(),
    val leftBumper: ButtonState = ButtonState.NOT_PRESSED,
    val rightBumper: ButtonState = ButtonState.NOT_PRESSED
) {
}