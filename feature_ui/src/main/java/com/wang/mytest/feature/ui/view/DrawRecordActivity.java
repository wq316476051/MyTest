package com.wang.mytest.feature.ui.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.wang.mytest.feature.ui.R;

import java.io.File;
import java.io.FilenameFilter;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;

public class DrawRecordActivity extends FragmentActivity {

    private FilenameFilter mFilenameFilter = (dir, name) -> {
        return name.startsWith("draw_") && name.endsWith(".png");
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_record);

        GridView recordListView = findViewById(R.id.record_list_view);

        File drawDirectory = getExternalFilesDir("draws");
        File[] files = drawDirectory.listFiles(mFilenameFilter);
        RecordAdapter recordAdapter = new RecordAdapter(files);
        recordListView.setAdapter(recordAdapter);

        recordListView.setOnItemClickListener((parent, view, position, id) -> {
            PreviewFragmentDialog.newInstance(files[position].getAbsolutePath())
                    .show(getSupportFragmentManager(), "draw_preview");
        });
    }

    private class RecordAdapter extends BaseAdapter {

        private File[] mFiles;

        public RecordAdapter(File[] files) {
            mFiles = files;
        }

        @Override
        public int getCount() {
            return mFiles != null ? mFiles.length : 0;
        }

        @Override
        public File getItem(int position) {
            return mFiles[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder(parent.getContext());
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.imageView.setImageBitmap(BitmapFactory.decodeFile(getItem(position).getAbsolutePath()));
            return holder.cardView;
        }

        private class ViewHolder {
            CardView cardView;
            ImageView imageView;

            ViewHolder(Context context) {
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                CardView.LayoutParams layoutParams = new CardView.LayoutParams(
                        (int) (displayMetrics.widthPixels / 4.5F), (int) (displayMetrics.heightPixels / 4.5F));
                layoutParams.gravity = Gravity.CENTER;

                imageView = new ImageView(context);
                imageView.setBackgroundResource(R.drawable.navigation_item_stroke);
                imageView.setLayoutParams(layoutParams);

                CardView.LayoutParams lp = new CardView.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                cardView = new CardView(context);
                cardView.addView(imageView);
                cardView.setTag(this);
            }
        }
    }
}
