package ru.sfti.go1ctl.high_level;

import android.util.Log;

import java.io.IOException;

import ru.sfti.go1ctl.sbk_java.SbkCtrlMessage;
import ru.sfti.go1ctl.sbk_java.SbkHighCtrlMessage;
import ru.sfti.go1ctl.sbk_java.SbkHighUdpSocket;
import ru.sfti.go1ctl.sbk_java.SbkModeEnum;
import ru.sfti.go1ctl.sbk_java.SbkUdpSocket;

public class KeyHandlerThread
    extends Thread
{
    private static final String _TAG = "KeyHandlerThread";

    public static final float DEFAULT_SPEED_RATE = 0.5f;
    public static final float DEFAULT_YAW_SPEED_RATE = 2f;

    public boolean l2Pressed;
    public boolean aPressed;

    public SbkModeEnum mode;

    public float leftJoystickX, leftJoystickY;
    public float rightJoystickX, rightJoystickY;

    public float speedRate;
    public float yawSpeedRate;

    private SbkHighUdpSocket _socket;
    private SbkHighCtrlMessage _ctrlMsg;


    public KeyHandlerThread(SbkHighUdpSocket socket)
    {
        super("gamepad_key_handler");

        this.l2Pressed = false;
        this.aPressed = false;

        this.speedRate = DEFAULT_SPEED_RATE;
        this.yawSpeedRate = DEFAULT_YAW_SPEED_RATE;

        this._ctrlMsg = new SbkHighCtrlMessage();
    }


    @Override
    public void run() {
        Log.println(Log.INFO, _TAG, "lowering the body");

        while (true) {
            boolean changed = false;

            if (l2Pressed && aPressed)


            // Handling joystick inputs
            if (mode == SbkModeEnum.TARGET_VELOCITY) {
                this._ctrlMsg.setVelocity(
                        leftJoystickX * speedRate,
                        leftJoystickY * speedRate
                );
                changed = true;
            }
            else if (mode == SbkModeEnum.FORCE_STAND) {}

            if (changed)
                Log.println(Log.DEBUG, _TAG, this._ctrlMsg.toString());

            try {
                if (this._socket != null) this._socket.send(this._ctrlMsg);
            } catch (IOException ex) {
                Log.println(Log.ERROR, _TAG, ex.toString());
            }
        }
    }
}
