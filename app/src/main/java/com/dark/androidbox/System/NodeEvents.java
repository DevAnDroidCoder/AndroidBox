package com.dark.androidbox.System;

import java.util.ArrayList;
import java.util.HashMap;

public interface NodeEvents {
    void NodeOnLongClick();

    void NodeListItemsOnClick(int pos, ArrayList<HashMap<String, Object>> data);

    void NodeListItemsOnLongClick();
}
