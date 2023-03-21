package com.dark.androidbox.Adpaters;

public class Codes {
    public int itemId;
    public String label;

    public Codes(int itemId, String label) {
        this.itemId = itemId;
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public int getItemId() {
        return itemId;
    }
}
