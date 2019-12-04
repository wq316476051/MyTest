package com.wang.mytest.big_screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PlaybackFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_big_playback, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_show).setOnClickListener(but -> {
            MyDialog myDialog = new MyDialog();
            myDialog.show(getChildFragmentManager(), "child");
        });

        view.findViewById(R.id.btn_show2).setOnClickListener(but -> {
            MyDialog myDialog = new MyDialog();
            myDialog.show(getFragmentManager(), "child");
        });
    }

    public boolean isEmpty() {
        return false;
    }
}
