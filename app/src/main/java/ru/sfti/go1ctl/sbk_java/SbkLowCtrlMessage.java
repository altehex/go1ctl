package ru.sfti.go1ctl.sbk_java;


public class SbkLowCtrlMessage
        extends SbkCtrlMessage
{
    private static final String  _TAG       = "SbkLowCtrlMessage";
    private static final int     _SIZE      = 614;
    private static final byte    _LEVEL     = (byte) 0xFF;
    private static final byte[]  _BANDWIDTH = { 0x3A, (byte) 0xC0 };
    private static final int     _MAGIC     = 0xEDCAB9DE;

    private static final int _CRC_OFF = 610;

    public SbkMotor[] joint;
    public SbkBms     bms;


    public SbkLowCtrlMessage()
    {
        super(_SIZE, _LEVEL, _CRC_OFF);

        this._packetBuffer[_BW_OFF]   = _BANDWIDTH[0];
        this._packetBuffer[_BW_OFF+1] = _BANDWIDTH[1];

        this._updateCrc32();
        this._update = false;
    }


    @Override
    protected int
    _genCrc32() {
        int crc = super._genCrc32();
        return Integer.rotateRight(crc, 8);
    }
}
