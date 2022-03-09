package com.blossom.alpacapaca.kkokkkogi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.blossom.alpacapaca.kkokkkogi.R;
import com.blossom.alpacapaca.kkokkkogi.wardactivity.WardMainActivity;


public class WardMainFragment extends Fragment {
    View rootView;
    TextView textView1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ward_main, container, false);
        textView1 = rootView.findViewById(R.id.fragment_ward_main_text1);
        textView1.setText(WardMainActivity.getWardName() + "님 안녕하세요!");
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}