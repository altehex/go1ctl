package ru.sfti.go1ctl.high_level;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import ru.sfti.go1ctl.databinding.FragmentMiniGamepadButtonContainerBinding;
import ru.sfti.go1ctl.sbk_java.SbkHighCtrlMessage;
import ru.sfti.go1ctl.sbk_java.SbkHighUdpSocket;


public class MiniGamepadButtonContainerFragment extends Fragment {
    private static final String _TAG = "MiniGamepadButtonContainerFragment";

    private FragmentMiniGamepadButtonContainerBinding _binding;

    private SbkHighUdpSocket _socket;

    private KeyHandlerThread _keyHandler;

    public MiniGamepadButtonContainerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this._binding = FragmentMiniGamepadButtonContainerBinding.inflate(getLayoutInflater());

        this._socket = ((HighLevelFragment) this.getParentFragment()).getSocket();

        this._keyHandler = new KeyHandlerThread(this._socket);
        this._keyHandler.start();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        this._binding.aButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                _keyHandler.aPressed = action == MotionEvent.ACTION_DOWN;
                return true;
            }
        });

        this._binding.l2Button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                _keyHandler.l2Pressed = action == MotionEvent.ACTION_DOWN;
                return true;
            }
        });

        return this._binding.getRoot();
    }
}