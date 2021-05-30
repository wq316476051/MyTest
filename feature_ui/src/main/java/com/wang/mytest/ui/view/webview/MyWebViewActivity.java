package com.wang.mytest.ui.view.webview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

;
import com.wang.mytest.ui.R;

import androidx.annotation.Nullable;

public class MyWebViewActivity extends Activity {

    private static final String TAG = "MyWebViewActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        setContentView(R.layout.activity_web_view);

        WebView webView = findViewById(R.id.web_view);
        Button btnTest = findViewById(R.id.btn_test);

        WebSettings webSettings = webView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 格式规定为:file:///android_asset/文件名.html
        webView.loadUrl("file:///android_asset/hello.html");

        webView.addJavascriptInterface(new JsInterface(), "android");

        btnTest.setOnClickListener(view -> {
            // 方法一
            webView.loadUrl("javascript:AndroidcallJS('hello')");

            // 方法二
//            webView.evaluateJavascript("javascript:AndroidcallJS()", new ValueCallback<String>() {
//                @Override
//                public void onReceiveValue(String value) {
//                    Log.i(TAG, "onReceiveValue: value = " + value);
//                }
//            });
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.i(TAG, "onJsAlert: url = " + url);
                Log.i(TAG, "onJsAlert: message = " + message);
                Log.i(TAG, "onJsAlert: result = " + result);
                AlertDialog.Builder mDialog= new AlertDialog.Builder(MyWebViewActivity.this);
                mDialog.setTitle("弹窗");
                mDialog.setMessage(message);
                mDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                mDialog.setCancelable(false);
                mDialog.create().show();
                return true;
            }
        });
    }

    private class JsInterface {
        @JavascriptInterface
        public void androidMethod(String content) {
            Log.i(TAG, "androidMethod: " + content);
        }

        @JavascriptInterface
        public void log(String msg) {
            Log.i(TAG, "log: " + msg);
        }
    }
}
