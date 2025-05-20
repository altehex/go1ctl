package ru.sfti.go1ctl;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.sfti.go1ctl.databinding.FragmentFeedbackBinding;
import ru.sfti.go1ctl.high_level.HighFeedbackReceiveThread;
import ru.sfti.go1ctl.high_level.HighLevelFeedbackViewAdapter;
import ru.sfti.go1ctl.sbk_java.SbkHighFbMessage;
import ru.sfti.go1ctl.sbk_java.SbkUdpSocket;
import ru.sfti.go1ctl.util.SbkUdpSocketViewModel;


public class FeedbackFragment extends Fragment {
    private FragmentFeedbackBinding _binding;

    private SbkUdpSocketViewModel _socketViewModel;
    private SbkUdpSocket _socket;

    private HighFeedbackReceiveThread _recvThread;
    private HighLevelFeedbackViewAdapter _listAdapter;


    public FeedbackFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this._binding = FragmentFeedbackBinding.inflate(getLayoutInflater());

        this._listAdapter = new HighLevelFeedbackViewAdapter();
        this._binding.packetFieldView.setLayoutManager(new LinearLayoutManager(getContext()));
        this._binding.packetFieldView.setAdapter(this._listAdapter);

        this._socketViewModel = new ViewModelProvider(requireActivity())
                .get(SbkUdpSocketViewModel.class);
        this._socket = this._socketViewModel.get();
        this._recvThread = new HighFeedbackReceiveThread(this, this._socket);
        this._recvThread.start();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        return this._binding.getRoot();
    }


    public void
    onNewMessage(SbkHighFbMessage fbMsg)
    {
        if (fbMsg == null) return;

        this._binding.packetRawView.setText(fbMsg.toString());
        this._listAdapter.setValues(fbMsg);
    }
}