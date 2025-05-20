package ru.sfti.go1ctl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import ru.sfti.go1ctl.databinding.FragmentMainMenuBinding;
import ru.sfti.go1ctl.high_level.HighLevelFragment;
import ru.sfti.go1ctl.low_level.LowLevelFragment;
import ru.sfti.go1ctl.ssh_client.SshClientFragment;


public class MainMenuFragment extends Fragment {
    private FragmentMainMenuBinding _binding;

    private static final int PAGE_COUNT = 2;
    private static final String[] _TAB_NAMES = {
        "Control board",
        "SSH Client"
    };

    private ControlBoardFragment _controlBoard;
    private SshClientFragment _sshClientFragment;


    public MainMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._binding = FragmentMainMenuBinding.inflate(getLayoutInflater());
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        this._sshClientFragment = new SshClientFragment();
        this._controlBoard = new ControlBoardFragment();

        // Set up tabs
        FragmentStateAdapter _viewPagerAdapter = new PagerAdapter(
                requireActivity(), this._sshClientFragment, this._controlBoard
        );

        this._binding.mainViewPager.setAdapter(_viewPagerAdapter);
        this._binding.mainViewPager.setUserInputEnabled(false);

        new TabLayoutMediator(
                this._binding.mainTabLayout,
                this._binding.mainViewPager,
                (tab, pos) -> tab.setText(_TAB_NAMES[pos]))
                .attach();

        return this._binding.getRoot();
    }


    private static class PagerAdapter
        extends FragmentStateAdapter
    {

        private final ControlBoardFragment _controlBoard;
        private final SshClientFragment _sshClientFragment;

        public PagerAdapter(@NonNull FragmentActivity fragmentActivity,
                            SshClientFragment sshClient,
                            ControlBoardFragment controlBoard) {
            super(fragmentActivity);

            this._sshClientFragment = sshClient;
            this._controlBoard = controlBoard;
        }

        @NonNull
        @Override
        public Fragment createFragment(int pos) {
            if (pos == 1) return this._sshClientFragment;
            else          return this._controlBoard;
        }

        @Override
        public int getItemCount() {
            return PAGE_COUNT;
        }
    }
}