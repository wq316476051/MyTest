package com.wang.lib_java.collection;

import java.util.ArrayList;

import javax.management.AttributeList;
import javax.management.relation.RoleList;

/**
 * Collection
 *      List
 *          ArrayList   非同步集合，数据实现
 *          LinkedList  非同步集合，链表实现
 *          Vector      同步集合，集合实现
 *              Stack   模仿栈结构
 *          CopyOnWriteArrayList    读的时候直接读，写的时候复制一份，所以不会发生“并发修改异常”
 *
 *          AttributeList           javax.management            不能在 android 中使用
 *          RoleList                javax.management.relation   不能在 android 中使用
 */
public class ListTest {

    public void test() {
        ArrayList<String> strings = null;
        strings.removeIf(String::isEmpty);
    }

    public void attribute() {
        AttributeList list = new AttributeList();
    }

    public void role() {
        RoleList list = new RoleList();
    }
}
