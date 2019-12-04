package com.wang.lib_viewlinker;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

import androidx.annotation.NonNull;

public class ViewLinker {

    /**
     * Invoke after {@link Activity#setContentView(View)}
     *
     * @param activity
     */
    public static void link(@NonNull Activity activity) {
        Field[] declaredFields = activity.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            Link bindView = field.getAnnotation(Link.class);
            if (bindView != null) {
                int viewId = bindView.value();
                try {
                    field.set(activity, activity.findViewById(viewId));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
