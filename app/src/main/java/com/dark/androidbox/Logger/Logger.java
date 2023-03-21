package com.dark.androidbox.Logger;

import android.content.Context;
import android.widget.TextView;

public class Logger {

    Context activity;

    public Logger(Context activity) {
        this.activity = activity;
    }

    public void R(TextView logger, StringBuilder tag, StringBuilder log) {

        logger.setText(logger.getText().toString().concat("\n")
                .concat(">> ")
                .concat("] Regular Log [ ")
                .concat(tag.toString())
                .concat(" ] : ")
                .concat(log.toString()));
    }

}
