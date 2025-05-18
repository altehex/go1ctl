package ru.sfti.go1ctl;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.sfti.go1ctl.databinding.FragmentFeedbackBinding;


public class FeedbackFragment extends Fragment {
    private FragmentFeedbackBinding _binding;

    public FeedbackFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this._binding = FragmentFeedbackBinding.inflate(getLayoutInflater());
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return this._binding.getRoot();
    }
}