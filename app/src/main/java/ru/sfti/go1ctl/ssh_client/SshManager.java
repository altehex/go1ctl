package ru.sfti.go1ctl.ssh_client;

import android.util.Log;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SshManager {
    private static final String _TAG = "SshManager";

    private static final String _GO1_ADDR = "192.168.12.1";
    private static final String _USER = "pi";
    private static final String _PASSWD = "123";

    private static final int _MAX_BUF_SIZE = 1048576;

    private SshConnectThread _connThread;

    private byte[] _buf;
    private int _bufSize;
    private boolean _finishedSession;


    public SshManager(int bufSize)
    {
        this._bufSize = bufSize;
        this._buf = new byte[bufSize];
        this._finishedSession = true;
    }


    public void
    sshConnect()
    {
        this._connThread = new SshConnectThread(this);
        this._connThread.start();
        this._finishedSession = false;
    }


    public int
    getBufSize() {
        return this._bufSize;
    }


    public void
    setBufSize(int size) {
        if (size < 0) return;

        this._bufSize = Math.min(size, _MAX_BUF_SIZE);
    }


    public byte[]
    getBuffer() {
        return this._buf;
    }


    public boolean
    isSessionFinished() {
        return this._finishedSession;
    }


    private class SshConnectThread
        extends Thread
    {
        private SshManager _manager;
        private SSHClient _client;
        private Session _session;
        private Session.Shell _shell;

        private InputStream _istream;
        private OutputStream _ostream;


        public SshConnectThread(SshManager manager)
        {
            this._manager = manager;
        }


        public void
        run()
        {
            int readLen;

            this._client = new SSHClient();
            this._client.addHostKeyVerifier(new PromiscuousVerifier());

            try {
                this._client.connect(_GO1_ADDR);
                this._client.authPassword(_USER, _PASSWD);

                this._session = this._client.startSession();
                this._shell = this._session.startShell();

                this._istream = this._shell.getInputStream();
                this._ostream = this._shell.getOutputStream();

            } catch (IOException e) {
                Log.println(Log.ERROR, _TAG, "Failed to establish SSH connection. SSH console won't work.");
                return;
            }

            try {
                do {
                    readLen = this._istream.read(this._manager._buf);
                } while (readLen != -1);

            } catch (IOException ex) {
                Log.println(Log.ERROR, _TAG, "Failed to fetch remote data to input buffer");
            }

            this._manager._finishedSession = true;
        }
    }
}
