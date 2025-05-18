package ru.sfti.go1ctl;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import ru.sfti.go1ctl.databinding.FragmentLevelSelectionBinding;
import ru.sfti.go1ctl.low_level.LowLevelFragment;
import ru.sfti.go1ctl.util.OnClickListenerNavCtrl;
import ru.sfti.go1ctl.util.OnClickRouteListener;

public class LevelSelectionFragment extends Fragment
{
    private FragmentLevelSelectionBinding _binding;

    private boolean _connectionConfirmed;


    public LevelSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this._binding = FragmentLevelSelectionBinding.inflate(getLayoutInflater());

        this._connectionConfirmed = false;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        NavHostFragment navHostFragment = (NavHostFragment) this.getParentFragment();
        assert navHostFragment != null;
        NavController navCtrl = navHostFragment.getNavController();

        this._binding.highLevelButton.setOnClickListener(new OnClickRouteListener(navCtrl, R.id.high_level_route));

        this._binding.lowLevelButton.setOnClickListener(
                new OnClickListenerNavCtrl(navCtrl, this) {
                    @Override
                    public void onClick(View v) {
                        if ( ! _connectionConfirmed) {
                            WarningDialogFragment warnDialog = new WarningDialogFragment((LevelSelectionFragment) _parent);
                            warnDialog.show(getActivity().getSupportFragmentManager(), "warn_low");
                        }

                        if (_connectionConfirmed)
                            this._navCtrl.navigate(R.id.low_level_route);
                    }
        });

        return this._binding.getRoot();
    }


    public static class WarningDialogFragment
            extends DialogFragment
    {
        LevelSelectionFragment _lsFrag;

        public WarningDialogFragment(LevelSelectionFragment lsFrag) {
            this._lsFrag = lsFrag;
        }

        @NonNull
        @Override
        public Dialog
        onCreateDialog(Bundle savedInstanceState)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = requireActivity().getLayoutInflater();

            builder.setView(inflater.inflate(R.layout.fragment_low_level_connetion_warning_dialog, null))
                    .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            _lsFrag._connectionConfirmed = true;
                        }
                    })
                    .setNegativeButton("Return", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            _lsFrag._connectionConfirmed = false;
                        }
                    });

            return builder.create();
        }
    }
}