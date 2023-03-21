package com.dark.androidbox;

import android.os.Bundle;
import android.os.Environment;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.dark.androidbox.Fragments.EditorFragment;
import com.dark.androidbox.Fragments.SetUpScreenOne;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    FrameLayout base_frame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        base_frame = findViewById(R.id.base_frame);

        if (Environment.isExternalStorageManager()) {
            File directory = new File(Environment.getExternalStorageDirectory() + "/AndroidBox/System");
            if (directory.exists()) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.base_frame, new EditorFragment())
                        .commit();
            }
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.base_frame, new SetUpScreenOne())
                    .commit();
        }


    }


}