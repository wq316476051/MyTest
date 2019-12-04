package com.wang.mytest.apt.annotation;

public class RouteBean {

    public RouteBean(String title, String path, String className) {
        this.title = title;
        this.path = path;
        this.className = className;
    }

    public String title;

    public String path;

    public String className;

    @Override
    public String toString() {
        return "RouteBean{" +
                "title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
