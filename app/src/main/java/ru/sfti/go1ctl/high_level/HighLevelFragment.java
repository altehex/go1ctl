package ru.sfti.go1ctl.high_level;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

import ru.sfti.go1ctl.R;
import ru.sfti.go1ctl.databinding.FragmentHighLevelBinding;
import ru.sfti.go1ctl.databinding.FragmentMiniGamepadButtonContainerBinding;
import ru.sfti.go1ctl.sbk_java.SbkHighCtrlMessage;
import ru.sfti.go1ctl.sbk_java.SbkUdpSocket;
import ru.sfti.go1ctl.util.OnClickListenerNavCtrl;
import ru.sfti.go1ctl.util.SbkUdpSocketViewModel;


public class HighLevelFragment extends Fragment {
    private static final String _TAG = "HighLevelFragment";

    private FragmentHighLevelBinding _binding;

    private SbkUdpSocketViewModel _socketViewModel;
    private SbkUdpSocket _socket;



    public HighLevelFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this._binding = FragmentHighLevelBinding.inflate(getLayoutInflater());

        this._socketViewModel = new ViewModelProvider(requireActivity())
                .get(SbkUdpSocketViewModel.class);
        this._socket = this._socketViewModel.get();

        try {
            if (this._socket != null) this._socket.setupConnection(new SbkHighCtrlMessage());
        } catch (InterruptedException | IOException e) {
            Log.println(Log.ERROR, _TAG, "Failed to establish high level connection. Nothing will work.");
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
                    public void
                    onClick(View v)
                    {
                        ((MiniGamepadButtonContainerFragment) _binding.miniGamepadButtonContainer.getFragment()).close();
                        this._navCtrl.popBackStack();
                    }
                }
        );

        this._binding.fullScreenGamepadButton.setOnClickListener(
                new OnClickListenerNavCtrl(navCtrl) {
                    @Override
                    public void
                    onClick(View v)
                    {
                        this._navCtrl.navigate(R.id.gamepad_route);
                    }
                }
        );

        return this._binding.getRoot();
    }
}