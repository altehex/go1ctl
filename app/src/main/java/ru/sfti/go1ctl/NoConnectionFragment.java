package ru.sfti.go1ctl;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import ru.sfti.go1ctl.databinding.FragmentNoConnectionBinding;
import ru.sfti.go1ctl.util.ConnectionManager;


public class NoConnectionFragment extends Fragment {
    private FragmentNoConnectionBinding _binding;

    private NavController _navController;

    private NavHostFragment _mainNavHostFragment;

    private ImageButton _connectButton;


    public NoConnectionFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this._binding = FragmentNoConnectionBinding.inflate(getLayoutInflater());
    }


    @Override
    public View
    onCreateView(@NonNull LayoutInflater inflater,
                 ViewGroup container,
                 Bundle savedInstanceState)
    {
        View v = this._binding.getRoot();

        this._navController = Navigation.findNavController((Activity) v.getContext(), R.id.main_nav_host);

        this._binding.connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navCtrl = Navigation.findNavController((Activity) v.getContext(), R.id.main_nav_host);

                if (ConnectionManager.isConnected())
                    navCtrl.navigate(R.id.connected_route);
                else {
                    FailedConnectionDialogFragment failDialog = new FailedConnectionDialogFragment();
                    failDialog.show(getActivity().getSupportFragmentManager(), "conn_fail");
                }

            }
        });

        // Inflate the layout for this fragment
        return v;
    }


    public static class FailedConnectionDialogFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog
        onCreateDialog(Bundle savedInstanceState)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage(R.string.failed_connection_prompt)
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });

            return builder.create();
        }
    }
}