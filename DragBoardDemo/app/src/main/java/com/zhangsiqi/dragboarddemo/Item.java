package com.zhangsiqi.dragboarddemo;

/**
 * Created by zhangsiqi on 2016/9/11.
 */
public class Item {
    private final String itemId;
    private final String itemName;
    private final String info;

    public Item(String itemId, String itemName, String info) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.info = info;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getInfo() {
        return info;
    }
}


