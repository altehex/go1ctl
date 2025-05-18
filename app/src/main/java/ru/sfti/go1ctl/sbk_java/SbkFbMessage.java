package ru.sfti.go1ctl.sbk_java;

import java.net.DatagramPacket;

public abstract class SbkFbMessage
    extends SbkMessage  {
    protected DatagramPacket _packet;

    protected abstract void _deserialize();


    public DatagramPacket
    getPacket() {
        return this._packet;
    }
}
