package ru.sfti.go1ctl.sbk_java;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.content.BroadcastReceiver;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

import androidx.annotation.NonNull;


public class SbkUdpSocket extends DatagramSocket {
    private static String _TAG = "SbkUdpSocket";


    protected
    SbkUdpSocket(SbkCtrlMessage msg,
                 int            port,
                 InetAddress    address)
            throws IOException
    {
        super(port, address);
        this.send(msg);
    }


    public void
    send(@NonNull SbkCtrlMessage msg)
            throws IOException
    {
        super.send(msg.toDatagram());
    }


    public void
    recv(SbkFbMessage msg)
            throws IOException
    {
        super.receive(msg.getPacket());
    }


    public void
    connectionPrompt()
    {
        NetworkRequest nr;
        BroadcastReceiver br;

        nr = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build();

    }
}
