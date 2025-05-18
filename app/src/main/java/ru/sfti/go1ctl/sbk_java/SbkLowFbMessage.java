package ru.sfti.go1ctl.sbk_java;

public class SbkLowFbMessage
        extends SbkFbMessage {
    public int SIZE = 807;

    private int[] _serialNumber;
    private int[] _version;
    private int   _bandwidth;
    private int[] _footForce;
    private int   _tick;
    private int   _crc;

    public SbkImu      imu;
    public SbkMotor[]  joints;
    public SbkBms      bms;
    public SbkJoystick joystick;


    public SbkLowFbMessage() { }

    public SbkLowFbMessage(byte[] packet) {
        this._packetBuffer = packet;
        this._deserialize();
    }


    @Override
    protected void _deserialize() {
    }
}
