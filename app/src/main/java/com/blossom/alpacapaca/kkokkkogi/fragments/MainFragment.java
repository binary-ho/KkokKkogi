package com.blossom.alpacapaca.kkokkkogi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blossom.alpacapaca.kkokkkogi.R;
import com.blossom.alpacapaca.kkokkkogi.useractivity.MainActivity;


public class MainFragment extends Fragment {
    View rootView;

    TextView title;
    TextView textView1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        textView1 = rootView.findViewById(R.id.fragment_main_text1);
        textView1.setText(MainActivity.getLoginUserName() + "님 안녕하세요!");
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        title = getActivity().findViewById(R.id.fragment_name);
        if(title != null) {
            title.setText("꼭꼭이");
        }
//        textView1 = getActivity().findViewById(R.id.fragment_main_text1);
//        textView1.setText(MainActivity.getLoginUserName() + "님 안녕하세요!");

    }
}