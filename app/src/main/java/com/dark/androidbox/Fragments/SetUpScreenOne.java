package com.dark.androidbox.Fragments;

import static com.dark.androidbox.Utilities.DarkUtilities.ShowMessage;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dark.androidbox.Logger.Logger;
import com.dark.androidbox.Managers.ActionRunner;
import com.dark.androidbox.Managers.DialogEvents;
import com.dark.androidbox.Managers.DialogManager;
import com.dark.androidbox.R;
import com.dark.androidbox.System.Actions;
import com.dark.androidbox.Utilities.SystemEvents;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.util.ArrayList;

public class SetUpScreenOne extends Fragment implements DialogEvents, SystemEvents {
    public static TextView loggerView;
    MaterialButton setup_btn;
    DialogManager dialogManager;
    ActionRunner runner;
    Logger logger;
    ScrollView logger_bg;
    private CountDownTimer countDownTimer;

    public SetUpScreenOne() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogManager = new DialogManager(getActivity(), this);
        runner = new ActionRunner(getActivity(), dialogManager, this);
        logger = new Logger(getContext());
        if (Environment.isExternalStorageManager()) {
            File directory = new File(Environment.getExternalStorageDirectory() + "/AndroidBox/System");
            if (directory.exists()) {
                Fragment fragment = new EditorFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.base_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_set_up_screen_one, container, false);
        setup_btn = root.findViewById(R.id.setup_btn);
        loggerView = root.findViewById(R.id.loggerView);
        logger_bg = root.findViewById(R.id.logger_bg);
        logger_bg.setY(2000f);

        loggerView.setText("Welcome To Android Box .. \n\n");

        logger.R(SetUpScreenOne.loggerView,
                new StringBuilder("Base Service"),
                new StringBuilder("Test Log"));

        setup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setup_btn.getText().toString().equals("Next")) {
                    Fragment fragment = new EditorFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.base_frame, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } else {
                    dialogManager.BasicDialog(
                            new StringBuilder("Proceed"),
                            new StringBuilder("System Manager !"),
                            new StringBuilder("Do you want to run The Actions ?"),
                            true);
                }
            }
        });


        if (Environment.isExternalStorageManager())
            onFilePermissionSuccess();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Environment.isExternalStorageManager()) {
            ShowMessage(getContext(), new StringBuilder("Storage Permission is ok"));
            onFilePermissionSuccess();
            if (dialogManager.basic_dlgBuilder != null) {
                dialogManager.CloseBasicDialog();
            }
        } else {
            runner.Run(Actions.CHECK_PERMISSIONS);
        }
    }

    @Override
    public void BtnClicked(Actions actions) {
        if (actions.getAction_position() == 3) {
            startActivity(new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION));
        } else {
            if (actions.getAction_position() == 1) {
                ArrayList<Object> data = new ArrayList<>();
                for (int i = 0; i <= dialogManager.adapter.getCount() - 1; i++) {
                    data.add(dialogManager.adapter.mData.get(i).getAction_position());
                }
                if (data.contains(Actions.SETUP_SYSTEM.getAction_position())) {
                    runner.Run(Actions.SETUP_SYSTEM);
                }
                if (data.contains(Actions.ACTIVATE_SERVICES.getAction_position())) {
                    runner.Run(Actions.ACTIVATE_SERVICES);
                }
                if (data.contains(Actions.CHECK_PERMISSIONS.getAction_position())) {
                    runner.Run(Actions.CHECK_PERMISSIONS);
                }

            }
        }
    }

    public void onFilePermissionSuccess() {
        if (setup_btn != null) {
            setup_btn.setIconResource(R.drawable.setup);
            setup_btn.setText(R.string.setup_btn_str);
            ObjectAnimator animator = ObjectAnimator.ofFloat(logger_bg, "translationY", 2000, 0);
            animator.setDuration(1500);
            animator.start();
        }
    }


    @Override
    public void SystemSetUpComplete() {
        countDownTimer = new CountDownTimer(2500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
            }

            @Override
            public void onFinish() {
                onSystemSet_UpComplete();
            }
        };

        countDownTimer.start();
    }

    public void onSystemSet_UpComplete() {
        if (setup_btn != null) {
            setup_btn.setIconResource(R.drawable.next);
            setup_btn.setText(R.string.next);
            ObjectAnimator animator = ObjectAnimator.ofFloat(logger_bg, "translationY", 0, 2000);
            animator.setDuration(1500);
            animator.start();
        }
    }
}