package com.wang.mytest.apt.api;

import com.wang.mytest.apt.annotation.RouteBean;

import java.util.Map;

public interface IRouteProvider {

    Map<String, RouteBean> get();
}
