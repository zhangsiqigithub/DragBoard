package com.zhangsiqi.dragboarddemo;

import java.util.List;

/**
 * Created by zhangsiqi on 2016/9/11.
 */
public class Entry {
    private final String id;
    private final String name;
    private final List<Item> itemList;

    public Entry(String id, String name, List<Item> items) {
        this.id = id;
        this.name = name;
        this.itemList = items;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Item> getItemList() {
        return itemList;
    }
}
