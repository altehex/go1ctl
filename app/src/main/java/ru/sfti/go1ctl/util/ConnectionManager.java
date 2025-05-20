package ru.sfti.go1ctl.util;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;

import java.io.IOException;

import ru.sfti.go1ctl.sbk_java.SbkUdpSocket;


public class ConnectionManager
{
    private final SbkUdpSocket _socket;


    public
    ConnectionManager()
            throws IOException, InterruptedException
    {
        this._socket = new SbkUdpSocket();
    }


    public SbkUdpSocket
    getSocket() {
        return this._socket;
    }


    public static class SetupWifiThread
            extends Thread
    {
        private final Activity _context;

        private boolean _summonWifiSettings;
        private boolean _finish;


        public
        SetupWifiThread(Activity context)
        {
            super("Wi-Fi setup thread");
            this._context = context;
            this._finish = false;
        }


        public void
        run()
        {
            while ( ! _finish) {
                if (this._summonWifiSettings) {
                    this._context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    this._summonWifiSettings = false;
                }
            }
        }


        public void
        summonWifiSettings() {
            this._summonWifiSettings = true;
        }


        public void
        finish() {
            this._finish = true;
        }
    }

}
