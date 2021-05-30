package com.wang.mytest.ui.view;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.wang.mytest.ui.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class PreviewFragmentDialog extends DialogFragment {

    private static final String TAG = "PreviewFragmentDialog";

    private static final String KEY_FILE_PATH = "file_path";

    public static DialogFragment newInstance(String filePath) {
        Bundle args = new Bundle();
        args.putString(KEY_FILE_PATH, filePath);

        DialogFragment fragment = new PreviewFragmentDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.fragment_draw_preview, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView ivPreview = view.findViewById(R.id.iv_preview);

        ivPreview.setImageBitmap(BitmapFactory.decodeFile(getArguments().getString(KEY_FILE_PATH)));
    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = (int) (displayMetrics.widthPixels * 0.9F);
        int height = (int) (displayMetrics.heightPixels * 0.9F);
        getDialog().getWindow().setLayout(width, height);
    }
}
