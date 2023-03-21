package com.dark.androidbox.Services;

import static com.dark.androidbox.Utilities.DarkUtilities.ShowMessage;

import android.content.Context;
import android.os.Environment;

import com.dark.androidbox.Fragments.SetUpScreenOne;
import com.dark.androidbox.Logger.Logger;
import com.dark.androidbox.Managers.DialogManager;

import java.io.File;

public class ActionsServices {

    Context activity;
    Logger logger;

    public ActionsServices(Context activity) {
        this.activity = activity;
        logger = new Logger(activity);
    }

    public void SetupFiles() {
        try {
            File directory = new File(Environment.getExternalStorageDirectory() + "/AndroidBox/System");
            boolean isSystemDir = directory.mkdirs();
            if (SetUpScreenOne.loggerView != null) {
                logger.R(SetUpScreenOne.loggerView,
                        new StringBuilder("FILE MANAGER"),
                        new StringBuilder(isSystemDir ? "FILE SET-UP COMPLETE" : "ERROR IN SETTING-UP FILES"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean CheckPermissions(DialogManager dialogManager) {
        boolean isStorage;
        if (!Environment.isExternalStorageManager()) {
            isStorage = false;
            dialogManager.BasicDialog(
                    new StringBuilder("Give"),
                    new StringBuilder("Permission Manager !"),
                    new StringBuilder("Please Provide The Necessary Permission"),
                    false);
            ShowMessage(activity, new StringBuilder("Provide Storage Permission"));
        } else {
            isStorage = true;
            ShowMessage(activity, new StringBuilder("Storage Permission is ok"));
            if (dialogManager.basic_dlgBuilder != null) {
                dialogManager.CloseBasicDialog();
            }
            //Provide Other Permissions Here...

        }
        return isStorage;
    }
}
