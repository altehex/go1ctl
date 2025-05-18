package ru.sfti.go1ctl.sbk_java;


public class SbkHighCtrlMessage
        extends SbkCtrlMessage
{
    private static final String  _TAG   = "SbkHighCtrlMessage";
    private static final int     _SIZE  = 129;
    private static final byte    _LEVEL = (byte) 0x00;

    private static final int _MODE_OFF        = 22;
    private static final int _GAIT_OFF        = 23;
    private static final int _FOOT_HEIGHT_OFF = 25;
    private static final int _BODY_HEIGHT_OFF = 29;
    private static final int _EULER_OFF       = 41;
    private static final int _VELOCITY_OFF    = 53;
    private static final int _YAW_SPEED_OFF   = 61;
    private static final int _BMS_OFF         = 65;
    private static final int _LED_OFF         = 69;
    private static final int _CRC_OFF         = 125;

    private float          _footHeightCached;
    private float          _bodyHeightCached;
    private final float[]  _eulerCached;
    private final float[]  _velocityCached;
    private float          _yawSpeedCached;

    public
    SbkHighCtrlMessage()
    {
        super(_SIZE, _LEVEL, _CRC_OFF);

        this._packetBuffer[0] = _HDR[0];
        this._packetBuffer[1] = _HDR[1];

        _eulerCached = new float[3];
        _velocityCached = new float[2];

        this._updateCrc32();
    }


    public void
    setMode(SbkModeEnum mode) {
        this._packetBuffer[_MODE_OFF] = (byte) mode.ordinal();
    }


    private void
    _setVelocity(float v, int off)
    {
        byte[] v32 = this._floatToFloat32(v);

        this._packetBuffer[_VELOCITY_OFF+off]   = v32[3];
        this._packetBuffer[_VELOCITY_OFF+off+1] = v32[2];
        this._packetBuffer[_VELOCITY_OFF+off+2] = v32[1];
        this._packetBuffer[_VELOCITY_OFF+off+3] = v32[0];
    }

    public void
    setVelocityX(float v)
    {
        if (v != this._velocityCached[0]) {
            this._setVelocity(v, 0);
            this._velocityCached[0] = v;
        }
    }

    public void
    setVelocityY(float v)
    {
        if (v != this._velocityCached[1]) {
            this._setVelocity(v, 4);
            this._velocityCached[1] = v;
        }
    }

    public void
    setVelocity(float vx, float vy)
    {
        this.setVelocityX(vx);
        this.setVelocityY(vy);
    }
}
