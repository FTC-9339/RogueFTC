package com.roguemafia.rogueftc.control

import com.qualcomm.robotcore.hardware.Gamepad

class JoypadState(val gamepad: Gamepad) {
    var callback: Gamepad.GamepadCallback = Gamepad.GamepadCallback {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    init {
        gamepad.copy(gamepad)
    }
}

interface JoypadCallback {
    open fun callback()
}

data class JoystickState(val x: Double = 0.0, val y: Double = 0.0, val deadzone: Double = 0.2) {
}

enum class ButtonState {
    NOT_PRESSED, PRESSED, HELD;

    fun updateButtonState(button: Boolean, state: ButtonState): ButtonState {
        when(button) {
            true -> if (state == NOT_PRESSED) {return PRESSED} else if (state == PRESSED) {return HELD}
            false -> if(state == PRESSED || state == HELD) return NOT_PRESSED
        }
        return HELD
    }
}