package ru.sfti.go1ctl.sbk_java;

import android.util.Log;

import androidx.annotation.NonNull;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
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

    private static final int    _PORT          = 8082;
    private static final String _WIFI_ADDR_STR = "192.168.12.195";

    private static InetAddress _WIFI_ADDR;


    static
    {
        try {
            _WIFI_ADDR = InetAddress.getByName(_WIFI_ADDR_STR);
        } catch (UnknownHostException e) {
            Log.println(Log.ERROR, _TAG, "Unknown host: " + _WIFI_ADDR_STR);
        }
    }


    public SbkCtrlMessage(int size, byte level, int crcOff)
    {
        super(size);

        this._SIZE    = size;
        this._CRC_OFF = crcOff;

        this._packetBuffer[_HDR_OFF]   = _HDR[0];
        this._packetBuffer[_HDR_OFF+1] = _HDR[1];

        this._packetBuffer[_LEVEL_OFF] = level;
        this._updateCrc32();

        this._packet = new DatagramPacket(this._packetBuffer, _SIZE);
        this._packet.setAddress(_WIFI_ADDR);
        this._packet.setPort(_PORT);
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
            this._packet.setData(this._packetBuffer);
        }

        return this._packet;
    }


    protected void
    _updateCrc32()
    {
        int crc = this._genCrc32();
        this._copy(crc, _CRC_OFF);
        this._update = false;
    }


    protected byte[]
    _toDword(int x)
    {
        this._dword.putInt(x);
        this._dword.rewind();
        return this._dword.array();
    }

    protected byte[]
    _toDword(float x)
    {
        this._dword.putFloat(x);
        this._dword.rewind();
        return this._dword.array();
    }


    protected void
    _copy(int x, int off)
    {
        byte[] x32 = this._toDword(x);
        this._copyDword(x32, off);
    }

    protected void
    _copy(float x, int off)
    {
        byte[] x32 = this._toDword(x);
        this._copyDword(x32, off);
    }

    protected void
    _copyDword(byte[] dword, int off)
    {
        this._packetBuffer[off]     = dword[3];
        this._packetBuffer[off + 1] = dword[2];
        this._packetBuffer[off + 2] = dword[1];
        this._packetBuffer[off + 3] = dword[0];
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

        Log.println(Log.WARN, _TAG, Integer.toHexString(crc));
        return crc;
    }
}
