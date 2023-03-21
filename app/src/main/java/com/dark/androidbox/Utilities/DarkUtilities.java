package com.dark.androidbox.Utilities;

import android.content.Context;
import android.widget.Toast;

public class DarkUtilities {


    public static void ShowMessage(Context ctx, StringBuilder txt) {
        Toast.makeText(ctx, txt, Toast.LENGTH_SHORT).show();
    }

}
