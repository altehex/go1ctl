package ru.sfti.go1ctl.sbk_java;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SbkHighUdpSocket
        extends SbkUdpSocket {
    public static final String TAG = "SbkHighUdpSocket";

    private static final byte _TYPE = (byte) 0x00;
    private static final int _PORT = 8082;

    private static final InetAddress _WIFI_ADDR;

    static
    {
        try {
            _WIFI_ADDR = InetAddress.getByName("192.168.12.1");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }


    public SbkHighUdpSocket()
            throws IOException
    {
        super(new SbkHighCtrlMessage(), _PORT, _WIFI_ADDR);
    }

}
