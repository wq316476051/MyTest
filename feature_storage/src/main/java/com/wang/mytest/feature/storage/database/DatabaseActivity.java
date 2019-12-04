package com.wang.mytest.feature.storage.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.wang.mytest.apt.annotation.Route;
import com.wang.mytest.feature.storage.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

@Route(path = "/activity/storage/database", title = "Database")
public class DatabaseActivity extends FragmentActivity {

    private static final String TAG = "DatabaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

//        openDatabase();
    }

    private void openDatabase() {
        SQLiteDatabase db = openOrCreateDatabase("test.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS products("
                + "prod_id TEXT NOT NULL PRIMARY KEY, "
                + "vend_id TEXT NOT NULL, "
                + "prod_name TEXT NOT NULL, "
                + "prod_price DECIMAL NOT NULL, "
                + "prod_desc TEXT NOT NULL"
                + ");"
        );

        db.execSQL("INSERT INTO products(prod_id, vend_id, prod_name, prod_price, prod_desc) VALUES('ID001', 'DLL001', 'XiaoShuiKu', 22.2, 'PuShui')");

        db.execSQL("INSERT INTO products(prod_id, vend_id, prod_name, prod_price, prod_desc) VALUES(?,?,?,?,?)",
                new Object[] {"ID002", "DLL002", "XiaoShuiKu2", 55.2, "PuShui2"});

        db.execSQL("INSERT INTO products(prod_id, vend_id, prod_name, prod_price, prod_desc) VALUES(?,?,?,?,?)",
                new Object[] {"ID003", "DLL003", "XiaoShuiKu3", 70.2, "PuShui3"});

        Cursor cursor = db.rawQuery("SELECT prod_id, prod_name, prod_price, prod_desc FROM products WHERE prod_price BETWEEN ? AND ?",
                new String[]{"22.2", "55.4"});
        Log.d(TAG, "onCreate: cursor = " + cursor);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String prod_id = cursor.getString(cursor.getColumnIndex("prod_id"));
                String prod_name = cursor.getString(cursor.getColumnIndex("prod_name"));
                double prod_price = cursor.getDouble(cursor.getColumnIndex("prod_price"));
                String prod_desc = cursor.getString(cursor.getColumnIndex("prod_desc"));
                Log.d(TAG, "onCreate: prod_id = " + prod_id);
                Log.d(TAG, "onCreate: prod_name = " + prod_name);
                Log.d(TAG, "onCreate: prod_price = " + prod_price);
                Log.d(TAG, "onCreate: prod_desc = " + prod_desc);
            }
        }
    }
}
