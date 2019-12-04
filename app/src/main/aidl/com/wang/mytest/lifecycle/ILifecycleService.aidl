// ILifecycleService.aidl
package com.wang.mytest.lifecycle;


interface ILifecycleService {
    void call(String method, String arge, in Bundle extras);
}
