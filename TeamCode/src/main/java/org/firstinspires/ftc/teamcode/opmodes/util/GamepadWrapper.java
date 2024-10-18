// This whole class is terribly inefficient... TOO BAD!
package org.firstinspires.ftc.teamcode.opmodes.util;

import com.qualcomm.robotcore.hardware.Gamepad;


// TODO: DOCUMENTATION!!!!!!111111!!!11!1111!
// TODO: CLEANLINESS!!!!111!!111!!!1!
// TODO: Test this class

public class GamepadWrapper {
    public class Button {
        private boolean last, current;

        public Button() {
            last = false;
            current = false;
        }

        public void update(boolean newValue) {
            last = current;
            current = newValue;
        }

        public boolean pressed() {
            return current;
        }

        public boolean released() {
            return !current;
        }

        public boolean justPressed() {
            return current && !last;
        }

        public boolean justReleased() {
            return !current && last;
        }
    }

    public class JoyStick {
        public final Button button;
        private float lastX, currentX;
        private float lastY, currentY;

        public JoyStick() {
            button = new Button();
            lastX = 0.0f;
            lastY = 0.0f;
            currentX = 0.0f;
            currentY = 0.0f;
        }

        public void update(float newX, float newY, boolean newButtonValue) {
            lastX = currentX;
            lastY = currentY;
            currentX = newX;
            currentY = newY;

            button.update(newButtonValue);
        }

        public float getX() {
            return currentX;
        }

        public float getY() {
            return currentY;
        }

        public float getXDifference() {
            return currentX - lastX;
        }

        public float getYDifference() {
            return currentY - lastY;
        }
    }


    private final Gamepad gamepad;

    public final Button a;
    public final Button b;
    public final Button x;
    public final Button y;

    public final Button rightBumper;
    public final Button leftBumper;

    public final Button dpadRight;
    public final Button dpadLeft;
    public final Button dpadUp;
    public final Button dpadDown;

    public final JoyStick leftStick;
    public final JoyStick rightStick;

    public GamepadWrapper(Gamepad base) {
        gamepad = base;

        a = new Button();
        b = new Button();
        x = new Button();
        y = new Button();

        rightBumper = new Button();
        leftBumper = new Button();

        dpadRight = new Button();
        dpadLeft = new Button();
        dpadUp = new Button();
        dpadDown = new Button();

        leftStick = new JoyStick();
        rightStick = new JoyStick();
    }

    public Gamepad getGamepad() {
        return gamepad;
    }

    public void update() {
        a.update(gamepad.a);
        b.update(gamepad.b);
        x.update(gamepad.x);
        y.update(gamepad.y);

        rightBumper.update(gamepad.right_bumper);
        leftBumper.update(gamepad.left_bumper);

        dpadRight.update(gamepad.dpad_right);
        dpadLeft.update(gamepad.dpad_left);
        dpadUp.update(gamepad.dpad_up);
        dpadDown.update(gamepad.dpad_down);

        leftStick.update(gamepad.left_stick_x, gamepad.left_stick_y, gamepad.left_stick_button);
        rightStick.update(gamepad.right_stick_x, gamepad.right_stick_y, gamepad.right_stick_button);
    }
}