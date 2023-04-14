package com.dark.androidbox.Managers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import com.dark.androidbox.Adpaters.ActionList;
import com.dark.androidbox.Fragments.SetUpScreenOne;
import com.dark.androidbox.R;
import com.dark.androidbox.StartUp.ActionRunner;
import com.dark.androidbox.StartUp.Actions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class DialogManager {

    public AlertDialog basic_dlgBuilder;
    public ActionRunner actionrunner;
    public ArrayList<Actions> data;
    public ActionList adapter;
    DialogEvents dialogEvents;
    Activity activity;
    MaterialAlertDialogBuilder builder;

    public DialogManager(Activity activity, DialogEvents dlg) {
        this.activity = activity;
        this.dialogEvents = dlg;
        actionrunner = new ActionRunner(activity, new SetUpScreenOne());
    }


    public void BasicDialog(StringBuilder txt_btn, StringBuilder title, StringBuilder message, boolean isAction) {
        builder = new MaterialAlertDialogBuilder(activity);

        if (isAction) {

            data = actionrunner.SetUpActions();

            adapter = new ActionList(data);

            View actionView = LayoutInflater.from(activity).inflate(R.layout.list_view, null);

            ListView action_list = actionView.findViewById(R.id.list_actions);

            action_list.setAdapter(adapter);

            builder.setView(action_list);

        }

        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .setNegativeButton(txt_btn, (dialog, which) -> dialogEvents.BtnClicked(isAction ? Actions.SETUP_SYSTEM : Actions.CHECK_PERMISSIONS));

        basic_dlgBuilder = builder.show();
    }

    public void CloseBasicDialog() {
        basic_dlgBuilder.dismiss();
    }

}
