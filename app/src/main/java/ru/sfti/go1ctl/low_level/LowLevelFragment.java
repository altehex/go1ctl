package ru.sfti.go1ctl.low_level;

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
import android.widget.Button;

import ru.sfti.go1ctl.LevelSelectionFragment;
import ru.sfti.go1ctl.R;
import ru.sfti.go1ctl.databinding.FragmentLowLevelBinding;
import ru.sfti.go1ctl.util.OnClickListenerNavCtrl;
import ru.sfti.go1ctl.util.OnClickRouteListener;


public class LowLevelFragment extends Fragment {
    private FragmentLowLevelBinding _binding;

    private JointControlActivity _jointControlActivity;

    private boolean _hangConfirmed;


    public LowLevelFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this._binding = FragmentLowLevelBinding.inflate(getLayoutInflater());

        this._hangConfirmed = false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        NavHostFragment navHostFragment = (NavHostFragment) this.getParentFragment();
        assert navHostFragment != null;
        NavController navCtrl = navHostFragment.getNavController();

        this._binding.lowCloseConnButton.setOnClickListener(
                new OnClickListenerNavCtrl(navCtrl)
        );

        this._binding.jointControlButton.setOnClickListener(
                new OnClickListenerNavCtrl(navCtrl, this) {
                    @Override
                    public void onClick(View v) {
                        if ( ! _hangConfirmed) {
                        HangWarningDialogFragment hangDialog =
                                new HangWarningDialogFragment((LowLevelFragment) _parent);
                        hangDialog.show(getActivity().getSupportFragmentManager(), "hang_warn_low");
    }

                        if (_hangConfirmed)
                            this._navCtrl.navigate(R.id.joint_control_route);
                    }
                }
        );

        return this._binding.getRoot();
    }


    public static class HangWarningDialogFragment
            extends DialogFragment
    {
        LowLevelFragment _llFrag;

        public HangWarningDialogFragment(LowLevelFragment llFrag) {
            this._llFrag = llFrag;
        }

        @NonNull
        @Override
        public Dialog
        onCreateDialog(Bundle savedInstanceState)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = requireActivity().getLayoutInflater();

            builder.setTitle(R.string.warning_title)
                    .setMessage("Please hang your dog and free the space around it")
                    .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            _llFrag._hangConfirmed = true;
                        }
                    })
                    .setNegativeButton("Return", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            _llFrag._hangConfirmed = false;
                        }
                    });

            return builder.create();
        }
    }
}