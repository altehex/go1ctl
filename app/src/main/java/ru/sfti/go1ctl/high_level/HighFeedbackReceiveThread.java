package ru.sfti.go1ctl.high_level;

import android.util.Log;

import java.io.IOException;
import java.net.SocketException;

import ru.sfti.go1ctl.FeedbackFragment;
import ru.sfti.go1ctl.sbk_java.SbkHighFbMessage;
import ru.sfti.go1ctl.sbk_java.SbkUdpSocket;

public class HighFeedbackReceiveThread
    extends Thread
{
    private static final String _TAG = "HighFeedbackReceiveThread";

    private SbkUdpSocket _socket;
    private SbkHighFbMessage _fbMsg;

    private FeedbackFragment _parent;


    public HighFeedbackReceiveThread(FeedbackFragment parent,
                                     SbkUdpSocket socket)
    {
        super("High level feedback receiving");
        this._socket = socket;
        this._fbMsg = new SbkHighFbMessage();

        this._parent = parent;
        this._parent.onNewMessage(this._fbMsg);
    }


    @Override
    public void
    run()
    {
        if (this._socket == null) {
            Log.println(Log.ERROR, _TAG, "SbkUdpSocket is null! Feedback monitor won't work");
            return;
        }

        try {
            this._socket.setReuseAddress(true);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            try {
                this._socket.recv(this._fbMsg);
                this._parent.onNewMessage(this._fbMsg);
            } catch (IOException e) {
                Log.println(Log.ERROR, _TAG, "Failed to receive a high level feedback message");
                break;
            }
        }
    }
}
