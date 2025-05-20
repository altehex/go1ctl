package ru.sfti.go1ctl;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.io.IOException;

import ru.sfti.go1ctl.databinding.FragmentNoConnectionBinding;
import ru.sfti.go1ctl.sbk_java.SbkHighCtrlMessage;
import ru.sfti.go1ctl.sbk_java.SbkUdpSocket;
import ru.sfti.go1ctl.util.ConnectionManager;
import ru.sfti.go1ctl.util.OnClickListenerNavCtrl;
import ru.sfti.go1ctl.util.SbkUdpSocketViewModel;


public class NoConnectionFragment extends Fragment {
    private static final String _TAG = "NoConnectionFragment";

    private FragmentNoConnectionBinding _binding;
    private FragmentManager _fragManager;

    private SbkUdpSocketViewModel _socketViewModel;
    private SbkUdpSocket _socket;

    private ConnectionManager _connManager;
    private ConnectionManager.SetupWifiThread _setupWifiThread;

    private NavController _navController;

    private FailedConnectionDialogFragment _failDialog;


    public NoConnectionFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this._binding = FragmentNoConnectionBinding.inflate(getLayoutInflater());
        this._fragManager = requireActivity().getSupportFragmentManager();

        this._failDialog = new FailedConnectionDialogFragment(this);

        // Temporarily establishing high level connection
        try {
            this._connManager = new ConnectionManager();
        } catch (IOException | InterruptedException e) {
            this._failDialog.show(this._fragManager, "conn_fail");
            Log.println(Log.ERROR, _TAG, e.toString());
        }

        this._socketViewModel = new ViewModelProvider(requireActivity())
            .get(SbkUdpSocketViewModel.class);

        this._setupWifiThread = new ConnectionManager.SetupWifiThread(this.getActivity());
        this._setupWifiThread.start();
    }


    @Override
    public View
    onCreateView(@NonNull LayoutInflater inflater,
                 ViewGroup container,
                 Bundle savedInstanceState)
    {
        View v = this._binding.getRoot();
        this._navController =
                Navigation.findNavController((Activity) v.getContext(), R.id.main_nav_host);

        this._binding.connectButton.setOnClickListener(
                new OnClickListenerNavCtrl(this._navController, this) {
                    @Override
                    public void onClick(View v) {
                        _setupWifiThread.finish();

                        this._navCtrl.navigate(R.id.connected_route);

                        try {
                            if (_connManager == null) _connManager = new ConnectionManager();
                        } catch (InterruptedException | IOException e) {
                            _failDialog.show(_fragManager, "conn_fail");
                            Log.println(Log.ERROR, _TAG, e.toString());
                            return;
                        }

                        _socketViewModel.set(_connManager.getSocket());
                        _setupWifiThread.finish();

                        this._navCtrl.navigate(R.id.connected_route);
                    }
                }
        );

        return v;
    }


    public void
    summonWifiSettings() {
        this._setupWifiThread.summonWifiSettings();
    }


    public static class FailedConnectionDialogFragment
            extends DialogFragment
    {
        private final NoConnectionFragment _noConnFrag;


        public
        FailedConnectionDialogFragment(NoConnectionFragment noConnFrag) {
            this._noConnFrag = noConnFrag;
        }


        @NonNull
        @Override
        public Dialog
        onCreateDialog(Bundle savedInstanceState)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage(R.string.failed_connection_prompt)
                    .setPositiveButton("OK", (dialog, which) -> _noConnFrag.summonWifiSettings())
                    .setNegativeButton("Close", (dialog, which) -> { return; });

            return builder.create();
        }
    }
}