package com.wang.mytest.ui.accessibility2;

import android.graphics.Rect;

public class VirtualView {
    public int id;
    public Rect bounds;
    public String content;

    public VirtualView(int id, Rect bounds, String content) {
        this.id = id;
        this.bounds = bounds;
        this.content = content;
    }
}
