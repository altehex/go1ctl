package ru.sfti.go1ctl;

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

    private TabLayout _tabLayout;
    private ViewPager2 _viewPager;

    private NavHostFragment _boardNavHostFragment;
    private LevelSelectionFragment _levelSelectionFragment;
    private SshClientFragment _sshClientFragment;


    public MainMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this._binding = FragmentMainMenuBinding.inflate(getLayoutInflater());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = this._binding.getRoot();
        Activity a = (Activity) v.getContext();

        // Set up tabs
        FragmentStateAdapter _viewPagerAdapter =
                new PagerAdapter((FragmentActivity) a);

        this._binding.mainViewPager.setAdapter(_viewPagerAdapter);

        new TabLayoutMediator(
                this._binding.mainTabLayout,
                this._binding.mainViewPager,
                (tab, pos) -> tab.setText(_TAB_NAMES[pos]))
                .attach();

        return v;
    }


    private static class PagerAdapter
        extends FragmentStateAdapter
    {
        public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int pos) {
            if (pos == 1) return new SshClientFragment();
            else          return new ControlBoardFragment();
        }

        @Override
        public int getItemCount() {
            return PAGE_COUNT;
        }
    }
}