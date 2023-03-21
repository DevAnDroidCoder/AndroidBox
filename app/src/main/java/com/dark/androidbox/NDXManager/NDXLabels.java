package com.dark.androidbox.NDXManager;

public class NDXLabels {
    public String label, Type, Data;

    public NDXLabels(String label, String Type, String Data) {
        this.label = label;
        this.Type = Type;
        this.Data = Data;
    }

    @Override
    public String toString() {
        return label;
    }


}
