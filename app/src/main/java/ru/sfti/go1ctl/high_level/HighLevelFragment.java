package ru.sfti.go1ctl.high_level;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

import ru.sfti.go1ctl.databinding.FragmentHighLevelBinding;
import ru.sfti.go1ctl.databinding.FragmentMiniGamepadButtonContainerBinding;
import ru.sfti.go1ctl.sbk_java.SbkHighUdpSocket;
import ru.sfti.go1ctl.util.OnClickListenerNavCtrl;


public class HighLevelFragment extends Fragment {
    private static final String _TAG = "HighLevelFragment";

    private FragmentHighLevelBinding _binding;
    private FragmentMiniGamepadButtonContainerBinding _miniGamepadBinding;

    protected SbkHighUdpSocket _socket;


    public HighLevelFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this._binding = FragmentHighLevelBinding.inflate(getLayoutInflater());

        try {
            this._socket = new SbkHighUdpSocket();
        } catch (IOException ex) {
            Log.println(Log.ERROR, _TAG, ex.toString());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        NavHostFragment navHostFragment = (NavHostFragment) this.getParentFragment();
        assert navHostFragment != null;
        NavController navCtrl = navHostFragment.getNavController();
;
        this._binding.highCloseConnButton.setOnClickListener(
                new OnClickListenerNavCtrl(navCtrl) {
                    @Override
                    public void onClick(View v) {
                        if (_socket != null) _socket.close();
                        this._navCtrl.popBackStack();
                    }
                }
        );

        return this._binding.getRoot();
    }


    SbkHighUdpSocket getSocket() {
        return this._socket;
    }
}