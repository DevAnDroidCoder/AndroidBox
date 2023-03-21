package com.dark.androidbox.System;

import androidx.annotation.NonNull;

public enum Actions {
    SETUP_SYSTEM("Set-Up System", 1),
    ACTIVATE_SERVICES("Activate Services", 2),
    CHECK_PERMISSIONS("System Permission", 3);

    private final String action_label;
    private final int action_position;

    Actions(String label, int pos) {
        action_label = label;
        action_position = pos;
    }

    @NonNull
    public String getAction_label() {
        return action_label;
    }

    public int getAction_position() {
        return action_position;
    }

}
