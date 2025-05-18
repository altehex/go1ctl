package ru.sfti.go1ctl.sbk_java;

import androidx.annotation.NonNull;

import java.net.DatagramPacket;

public abstract class SbkMessage {
    private String _TAG = "SbkMessage";

    protected static final byte[] _HDR = { (byte) 0xFE, (byte) 0xEF };

    protected static final int
            _HDR_OFF   = 0,
            _LEVEL_OFF = 2,
            _SN_OFF    = 4,
            _VER_OFF   = 12,
            _BW_OFF    = 20;

    protected DatagramPacket _packet;
    protected byte[]         _packetBuffer;


    @NonNull
    @Override
    public String
    toString()
    {
        StringBuilder s = new StringBuilder();

        for (byte b : this._packetBuffer) s.append(String.format("%02X", b));
        return s.toString();
    }
}
