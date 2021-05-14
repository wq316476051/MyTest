package com.wang.mytest.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wang.mytest.apt.annotation.Route;
import com.wang.mytest.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

/**
 * Demonstrates how to show an AlertDialog that is managed by a Fragment.
 */
@Route(path = "/activity/ui/fragment/Alert", title = "FragmentAlert")
public class FragmentAlertDialogSupport extends FragmentActivity {
    
    private static final String TAG = "FragmentAlertDialogSupport";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_dialog);

        TextView tv = findViewById(R.id.text);
        tv.setText("Example of displaying an alert dialog with a DialogFragment");

        Button button = findViewById(R.id.show);
        button.setOnClickListener(v -> {
            showDialog();
        });
    }

    void showDialog() {
        DialogFragment newFragment = MyAlertDialogFragment.newInstance(
                "alert_dialog_two_buttons_title");
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    public void doPositiveClick() {
        Log.i("FragmentAlertDialog", "Positive click!");
    }

    public void doNegativeClick() {
        Log.i("FragmentAlertDialog", "Negative click!");
    }

    public static class MyAlertDialogFragment extends DialogFragment {

        public static MyAlertDialogFragment newInstance(String title) {
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putString("title", title);
            frag.setArguments(args);
            return frag;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            Log.d(TAG, "onCreateView: ");
            TextView textView = new TextView(getActivity());
            textView.setText("From onCreateView");
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return textView;
//            return super.onCreateView(inflater, container, savedInstanceState);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Log.d(TAG, "onViewCreated: ");
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Log.d(TAG, "onCreateDialog: ");
            String title = getArguments().getString("title");
            return new AlertDialog.Builder(getActivity())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(title)
                    .setPositiveButton("alert_dialog_ok", (dialog, whichButton) -> {
                            ((FragmentAlertDialogSupport)getActivity()).doPositiveClick();
                    })
                    .setNegativeButton("alert_dialog_cancel", (dialog, whichButton) -> {
                        ((FragmentAlertDialogSupport)getActivity()).doNegativeClick();
                    })
                    .create();
        }
    }
}

