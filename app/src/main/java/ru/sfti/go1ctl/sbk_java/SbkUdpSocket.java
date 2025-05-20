package ru.sfti.go1ctl.sbk_java;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import androidx.annotation.NonNull;


public class SbkUdpSocket
        extends DatagramSocket
{
    private static String _TAG = "SbkUdpSocket";

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


    public
    SbkUdpSocket()
            throws IOException, InterruptedException
    {
        super(_PORT, _WIFI_ADDR);
    }


    public void
    send(@NonNull SbkCtrlMessage msg)
            throws IOException, InterruptedException
    {
        SendThread sendThread = new SendThread(this, msg);
        sendThread.start();
        sendThread.join();
    }


    public void
    recv(SbkFbMessage msg)
            throws IOException
    {
        super.receive(msg.getPacket());
    }


    public boolean
    setupConnection(SbkCtrlMessage msg)
            throws IOException, InterruptedException
    {
        this.send(msg.toDatagram());
        return msg.toDatagram().getLength() != 0;
    }


    private static class SendThread
        extends Thread
    {
        private final SbkUdpSocket _socket;
        private final SbkCtrlMessage _ctrlMsg;

        public SendThread(SbkUdpSocket socket, SbkCtrlMessage ctrlMsg)
        {
            this._socket = socket;
            this._ctrlMsg = ctrlMsg;
        }


        @Override
        public void
        run()
        {
            try {
                this._socket.send(_ctrlMsg);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
