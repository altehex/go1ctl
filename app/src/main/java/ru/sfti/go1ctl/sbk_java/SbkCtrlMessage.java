package ru.sfti.go1ctl.sbk_java;

import androidx.annotation.NonNull;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class SbkCtrlMessage
        extends SbkMessage
{
    private static final String _TAG = "SbkCtrlMessage";

    private static final int _CRC32_MAGIC = 0x04C1_1DB7;

    private final int _SIZE;
    private final int _CRC_OFF;

    protected int _crc;
    protected boolean _update;

    private ByteBuffer _int32;
    private ByteBuffer _float32;


    protected
    SbkCtrlMessage(int size, byte level, int crcOff)
    {
        this._SIZE    = size;
        this._CRC_OFF = crcOff;

        this._packetBuffer = new byte[_SIZE];

        this._packetBuffer[_HDR_OFF]   = _HDR[0];
        this._packetBuffer[_HDR_OFF+1] = _HDR[1];

        this._packetBuffer[_LEVEL_OFF] = level;

        this._int32 = ByteBuffer.allocate(4);
        this._float32 = ByteBuffer.allocate(4);
    }


    @NonNull
    @Override
    public final String
    toString()
    {
        StringBuilder s = new StringBuilder();
        if (this._update) this._updateCrc32();
        return super.toString();
    }


    public DatagramPacket
    toDatagram()
    {
        if (_update) {
            this._updateCrc32();
            this._packet = new DatagramPacket(this._packetBuffer, _SIZE);
        }

        return this._packet;
    }


    protected void
    _updateCrc32()
    {
        int crc = this._genCrc32();
        byte[] crcBytes = _intToInt32(crc);

        this._packetBuffer[this._CRC_OFF]   = crcBytes[3];
        this._packetBuffer[this._CRC_OFF+1] = crcBytes[2];
        this._packetBuffer[this._CRC_OFF+2] = crcBytes[1];
        this._packetBuffer[this._CRC_OFF+3] = crcBytes[0];

        this._update = false;
    }

    protected byte[]
    _intToInt32(int x) {
        this._int32.putInt(x);
        this._int32.rewind();
        return this._int32.array();
    }

    protected byte[]
    _floatToFloat32(float x) {
        this._float32.putFloat(x);
        this._float32.rewind();
        return this._float32.array();
    }

    protected int
    _genCrc32()
    {
        int crc, x, y, j;

        crc = 0xFFFF_FFFF;
        for (int i = 0; i < (_SIZE-4)>>>2; ++i) {
            j = i<<2;
            y = ((int) this._packetBuffer[j] & 0xFF)
                    | (((int) this._packetBuffer[j + 1] & 0xFF) << 8)
                    | (((int) this._packetBuffer[j + 2] & 0xFF) << 16)
                    | (((int) this._packetBuffer[j + 3] & 0xFF) << 24);

            for (int b = 0; b < 32; ++b) {
                x = (crc >>> 31) & 1;
                crc <<= 1;
                if ((x ^ (1 & (y >>> (31 - b)))) != 0)
                    crc ^= _CRC32_MAGIC;
            }
        }

        return crc;
    }
}
