package ru.sfti.go1ctl.ssh_client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.termux.shared.terminal.TermuxTerminalSessionClientBase;
import com.termux.shared.terminal.TermuxTerminalViewClientBase;
import com.termux.terminal.TerminalEmulator;
import com.termux.terminal.TerminalOutput;
import com.termux.terminal.TerminalSession;
import com.termux.terminal.TerminalSessionClient;
import com.termux.view.TerminalView;
import com.termux.view.TerminalViewClient;

import ru.sfti.go1ctl.databinding.FragmentSshClientBinding;


public class SshClientFragment extends Fragment {
    private FragmentSshClientBinding _binding;

    private SshManager _sshManager;
    private SshClientUpdateThread _updateThread;

    private TerminalViewClient _termClient;


    public SshClientFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this._binding = FragmentSshClientBinding.inflate(getLayoutInflater());

        this._termClient = new TermuxTerminalViewClientBase();
        this._binding.sshClientTerminalEmulator.setTerminalViewClient(this._termClient);

        //this._termClient.

        this._sshManager = new SshManager(8192);
        this._sshManager.sshConnect();
        ;

        this._updateThread = new SshClientUpdateThread(this);
        this._updateThread.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return this._binding.getRoot();
    }


    private class SshClientUpdateThread
        extends Thread
    {
        private SshClientFragment _parent;


        public SshClientUpdateThread(SshClientFragment parent)
        {
            this._parent = parent;
        }


        public void
        run()
        {
            byte[] buf;
            SshManager manager;
            TerminalView termView;

            manager = this._parent._sshManager;
            termView = this._parent._binding.sshClientTerminalEmulator;

            if (termView.mEmulator == null) return;

            while ( ! manager.isSessionFinished()) {
                buf = manager.getBuffer();
                ;
            }
        }
    }
}