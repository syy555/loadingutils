package com.cat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by lee on 2017/12/8.
 */

public class MainActivity extends AppCompatActivity {

    LoadingAadpter loadingAadpter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingAadpter = LoadingHelper.INSTANCE.LoadingHelper(getSupportFragmentManager(), R.id.container, new Runnable() {
            @Override
            public void run() {
                loadingAadpter.hide();
            }
        });
        findViewById(R.id.tv_loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingAadpter.showLoading();
                findViewById(R.id.tv_loading).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingAadpter.hide();
                    }
                }, 2000);
            }
        });

        findViewById(R.id.tv_dialog_loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingAadpter.showDialogLoading();
                findViewById(R.id.tv_loading).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingAadpter.hide();
                    }
                }, 2000);
            }
        });

        findViewById(R.id.tv_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingAadpter.showLoaderr();

            }
        });

    }


}
