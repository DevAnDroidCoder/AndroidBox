package com.dark.androidbox.Adpaters;

public class Codes {
    public int itemId;
    public String label;

    public StringBuilder data;

    public Codes(int itemId, String label, StringBuilder data) {
        this.itemId = itemId;
        this.label = label;
        this.data = data;
    }

    @Override
    public String toString() {
        return label;
    }

    public int getItemId() {
        return itemId;
    }
}
