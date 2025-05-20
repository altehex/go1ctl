package ru.sfti.go1ctl.sbk_java;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public abstract class SbkFbMessage
    extends SbkMessage  {
    protected DatagramPacket _packet;

    protected abstract void _deserialize();


    public SbkFbMessage(int size) {
        super(size);

        _dword = ByteBuffer.allocate(4);
    }


    public DatagramPacket
    getPacket() {
        return this._packet;
    }


    protected float
    _toFloat(int off)
    {
        this._dword.put(this._packetBuffer, off, 4);
        return this._dword.getFloat();
    }

    protected int
    _toInt(int off)
    {
        this._dword.put(this._packetBuffer, off, 4);
        return this._dword.getInt();
    }

    protected short
    _toShort(int off)
    {
        this._short.put(this._packetBuffer, off, 2);
        return this._short.getShort();
    }
}
