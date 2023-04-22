package com.dark.androidbox.Editor;

public class Codes {
    public int itemId;
    public String label;

    public StringBuilder data;

    public int type;

    public Codes(int type, int itemId, String label, StringBuilder data) {
        this.itemId = itemId;
        this.label = label;
        this.data = data;
        this.type = type;
    }

    @Override
    public String toString() {
        return label;
    }

    public int getItemId() {
        return itemId;
    }

    public void writeData(StringBuilder data){
        this.data = data;
    }
}
