package com.dark.androidbox.Editor;

public class Codes {
    public int itemId;
    public String label;

    public StringBuilder data;

    public String type;

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
