package ru.sfti.go1ctl.sbk_java;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SbkLowUdpSocket
        extends SbkUdpSocket
{
    public static final String TAG = "SbkLowUdpSocket";

    private static final byte _TYPE = (byte) 0xFF;
    private static final int _PORT = 8007;

    private static final InetAddress _MCU_ADDR;

    static
    {
        try {
            _MCU_ADDR = InetAddress.getByName("192.168.123.10");
        } catch (UnknownHostException e) {

            throw new RuntimeException(e);
        }
    }

    public SbkLowUdpSocket()
            throws IOException
    {
        super(new SbkLowCtrlMessage(), _PORT, _MCU_ADDR);
    }

}
