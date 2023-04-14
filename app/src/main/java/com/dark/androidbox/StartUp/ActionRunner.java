package com.dark.androidbox.StartUp;

import android.app.Activity;

import com.dark.androidbox.Fragments.SetUpScreenOne;
import com.dark.androidbox.Logger.Logger;
import com.dark.androidbox.Managers.DialogManager;
import com.dark.androidbox.System.SystemEvents;

import java.util.ArrayList;
import java.util.Collections;

public class ActionRunner {
    // Action Runner Manager

    public Activity ctx;

    DialogManager dialogManager;

    ActionsServices services;

    Logger logger;

    SystemEvents systemEvents;

    public ActionRunner(Activity ctx, SystemEvents systemEvents) {
        this.ctx = ctx;
        services = new ActionsServices(ctx);
        logger = new Logger(ctx);
        this.systemEvents = systemEvents;
    }

    public ActionRunner(Activity ctx, DialogManager dialogManager, SystemEvents systemEvents) {
        this.ctx = ctx;
        this.dialogManager = dialogManager;
        logger = new Logger(ctx);
        services = new ActionsServices(ctx);
        this.systemEvents = systemEvents;
    }

    public ArrayList<Actions> SetUpActions() {
        ArrayList<Actions> data = new ArrayList<>();

        Collections.addAll(data, Actions.values());
        return data;
    }

    public void Run(Actions actions) {
        switch (actions) {
            case SETUP_SYSTEM:
                services.SetupFiles();
                logger.R(SetUpScreenOne.loggerView, new StringBuilder("SET-UP SYSTEM"), new StringBuilder("Setting-Up System"));
                break;

            case ACTIVATE_SERVICES:
                logger.R(SetUpScreenOne.loggerView, new StringBuilder("SERVICE MANAGER"), new StringBuilder("Activating Services"));
                break;

            case CHECK_PERMISSIONS:
                logger.R(SetUpScreenOne.loggerView, new StringBuilder("PERMISSION MANAGER"), new StringBuilder("Checking Permissions...."));
                if (services.CheckPermissions(dialogManager)) {
                    logger.R(SetUpScreenOne.loggerView, new StringBuilder("PERMISSION MANAGER"), new StringBuilder("System Permissions are ok "));
                    systemEvents.SystemSetUpComplete();
                } else {
                    logger.R(SetUpScreenOne.loggerView, new StringBuilder("PERMISSION MANAGER"), new StringBuilder("Need to Provide System Permission"));
                }
                break;
        }
    }


}
