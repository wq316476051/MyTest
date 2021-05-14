package com.wang.mytest.common.acitivity;

import android.content.ComponentName;

import java.io.File;
import java.util.List;

public class TestBean {
    public static final int ACTIVITY = 1;
    public static final int FOLDER = 2;

    private int type;

    private String summary;

    private String path;

    private String[] pathSegment;

    private ComponentName componentName;

    private TestBean mPrevious;

    private List<TestBean> mNext;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String[] getPathSegment() {
        return pathSegment;
    }

    public void setPathSegment(String[] pathSegment) {
        this.pathSegment = pathSegment;
    }

    public ComponentName getComponentName() {
        return componentName;
    }

    public void setComponentName(ComponentName componentName) {
        this.componentName = componentName;
    }

    public TestBean getPrevious() {
        return mPrevious;
    }

    public void setPrevious(TestBean previous) {
        mPrevious = previous;
    }

    public List<TestBean> getNext() {
        return mNext;
    }

    public void setNext(List<TestBean> next) {
        mNext = next;
    }

    public static TestBean createActivity(String summary, String path, ComponentName componentName) {
        TestBean bean = new TestBean();
        bean.type = ACTIVITY;
        bean.summary = summary;
        bean.path = path;
        bean.pathSegment = path.split(File.pathSeparator);
        bean.componentName = componentName;
        return bean;
    }

    public static TestBean createFolder(String summary, String path, ComponentName componentName) {
        TestBean bean = new TestBean();
        bean.type = FOLDER;
        bean.summary = summary;
        bean.path = path;
        bean.pathSegment = path.split(File.pathSeparator);
        bean.componentName = componentName;
        return bean;
    }

    public boolean match(String prefix) {
        return path.startsWith(prefix);
    }

    public String getSummary() {
        return summary;
    }
}
