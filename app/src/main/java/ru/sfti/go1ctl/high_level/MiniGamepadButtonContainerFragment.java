package ru.sfti.go1ctl.high_level;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

import ru.sfti.go1ctl.databinding.FragmentMiniGamepadButtonContainerBinding;
import ru.sfti.go1ctl.sbk_java.SbkUdpSocket;
import ru.sfti.go1ctl.util.SbkUdpSocketViewModel;


public class MiniGamepadButtonContainerFragment
        extends Fragment
{
    private static final String _TAG = "MiniGamepadButtonContainerFragment";

    private FragmentMiniGamepadButtonContainerBinding _binding;

    private SbkUdpSocketViewModel _socketViewModel;
    private SbkUdpSocket _socket;

    private KeyHandlerThread _keyHandler;

    public MiniGamepadButtonContainerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this._binding = FragmentMiniGamepadButtonContainerBinding.inflate(getLayoutInflater());

        this._socketViewModel = new ViewModelProvider(requireActivity())
                .get(SbkUdpSocketViewModel.class);
        this._socket = this._socketViewModel.get();

        try {
            this._keyHandler = new KeyHandlerThread(this._socket);
        } catch (InterruptedException | IOException e) {
            Log.println(Log.ERROR, _TAG, "Failed to initialize high level connection: gamepad won't work");
        }
        this._keyHandler.start();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        this._binding.aButton.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            _keyHandler.aPressed = action == MotionEvent.ACTION_DOWN;
            return true;
        });

        this._binding.l2Button.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            _keyHandler.l2Pressed = action == MotionEvent.ACTION_DOWN;
            return true;
        });

        return this._binding.getRoot();
    }


    public void
    close() {
        this._keyHandler.finish();
    }
}