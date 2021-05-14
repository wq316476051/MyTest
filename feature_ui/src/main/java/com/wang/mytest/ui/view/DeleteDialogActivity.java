package com.wang.mytest.ui.view;

import android.app.AlertDialog;
import android.os.Bundle;

import com.wang.mytest.apt.annotation.Route;
import com.wang.mytest.R;
import com.wang.mytest.common.util.ToastUtils;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

@Route(path = "/activity/ui/view/delete", title = "Delete")
public class DeleteDialogActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_dialog);

//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.container, new DeleteDialogFragment())
//                .commit();

//        new DeleteDialogFragment().show(getSupportFragmentManager(), "delete");

        findViewById(R.id.btn_show).setOnClickListener(view -> {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("This is title")
                    .setMessage("This is message")
                    .setPositiveButton("确定", null)
                    .setNegativeButton("取消", null)
                    .show();
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(button -> {
                ToastUtils.INSTANCE.showShort("confirm clicked");
            });
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(button -> {
                ToastUtils.INSTANCE.showShort("cancel clicked");
            });
        });
    }
}
