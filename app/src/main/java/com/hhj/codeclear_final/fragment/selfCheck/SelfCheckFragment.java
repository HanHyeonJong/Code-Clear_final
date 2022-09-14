package com.hhj.codeclear_final.fragment.selfCheck;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.hhj.codeclear_final.databinding.FragmentSelfCheckBinding;

public class SelfCheckFragment extends Fragment {

    //뷰 바인딩
    private FragmentSelfCheckBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelfCheckBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}